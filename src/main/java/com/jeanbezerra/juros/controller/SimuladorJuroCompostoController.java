package com.jeanbezerra.juros.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.jeanbezerra.juros.entity.ResultadoSimuladorEntity;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class SimuladorJuroCompostoController implements Serializable {

	private static final long serialVersionUID = 5845591274542454155L;
	private Double taxaSelic;
	private Double valorInicial;
	private Double aporteMensal;
	private Integer meses;
	private Boolean pularPrimeiroMes;
	
	private Date currentDate;
	
	private LineChartModel lineModel = new LineChartModel();

	private List<ResultadoSimuladorEntity> resultadoSimuladorList = new ArrayList<>(100);
	
	/**
	 * Variaveis dos BigNumbers
	 */
	
	private Double bigNumberValorTotalInvestido = 0.0;
	private Double bigNumberValorTotalDeJuros = 0.0;

	public SimuladorJuroCompostoController() {
		
		this.currentDate = new Date();
		
		if(resultadoSimuladorList.size() > 0) {
			gerarGrafico();	
		}
	}
	
	public void buttonResetarSimulador() {
		try {
			
			this.lineModel = new LineChartModel();
			
			this.resultadoSimuladorList.clear();
			this.resultadoSimuladorList = new ArrayList<>(100);
			
			this.taxaSelic = 0.0;
			this.valorInicial = 0.0;
			this.aporteMensal = 0.0;
			this.meses = 0;			
			this.pularPrimeiroMes = Boolean.FALSE;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gerarGrafico() {		
		try {
			
			this.lineModel = new LineChartModel();
	        ChartData data = new ChartData();
			
	        
	        LineChartDataSet dataSet = new LineChartDataSet();
	        
	        List<Object> values = new ArrayList<>();
	        
	        for (ResultadoSimuladorEntity dataValue  : this.resultadoSimuladorList) {
	        	values.add(dataValue.getSubTotal());
			}
	        
	        dataSet.setData(values);
	        dataSet.setFill(false);
	        dataSet.setLabel("Valor");
	        dataSet.setBorderColor("rgb(75, 192, 192)");
	        dataSet.setTension(0.1);
	        data.addChartDataSet(dataSet);
	        
	        
	        List<String> labels = new ArrayList<>();
	        for (ResultadoSimuladorEntity dataLabel  : this.resultadoSimuladorList) {
	        	labels.add(dataLabel.getMes().toString());
			}	        
	        
	        data.setLabels(labels);
	        
	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Timeline de Investimento");
	        
	        Animation animation = new Animation();
	        animation.setDuration(1000);
	        animation.setDelay(100);
	        animation.setEasing("linear");
	        
	        //Options
	        LineChartOptions options = new LineChartOptions();
	        options.setTitle(title);
	        options.setAnimation(animation);
	        

	        this.lineModel.setOptions(options);
	        this.lineModel.setData(data);
	        
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calcBigNumbers() {
		try {
			this.bigNumberValorTotalInvestido = 0.0;
			this.bigNumberValorTotalDeJuros = 0.0;
			
			if(this.resultadoSimuladorList.size()>0) {
				this.bigNumberValorTotalInvestido = this.resultadoSimuladorList.get(this.resultadoSimuladorList.size()-1).getSubTotal();
				
				for (ResultadoSimuladorEntity resultadoSimuladorEntity : this.resultadoSimuladorList) {
					this.bigNumberValorTotalDeJuros = (this.bigNumberValorTotalDeJuros + resultadoSimuladorEntity.getValorJuros());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void buttonSimularInvestimento() {
		try {
			
			this.resultadoSimuladorList.clear();
			
			Double primeiroSubTotal = 0.0;
			Double ultimoSubTotal = 0.0;
			int ultimoMes = 1;
			final String simuladorId = UUID.randomUUID().toString();
			for (int i = 0; i < this.meses; i++) {
								
				if(i == 0) {
					ResultadoSimuladorEntity calcRow = new ResultadoSimuladorEntity();
					calcRow.setId(simuladorId);
					calcRow.setMes(ultimoMes);
					calcRow.setValor(this.valorInicial);
					
					if(this.pularPrimeiroMes) {
						calcRow.setAporte(0.0);
					}else {
						calcRow.setAporte(this.aporteMensal);
					}
					
					calcRow.setSomatoria(calcRow.getValor() + calcRow.getAporte());					
					calcRow.setJuros(this.taxaSelic/12);
					calcRow.setValorJuros( (calcRow.getSomatoria() * calcRow.getJuros() ) / 100);
					calcRow.setSubTotal(calcRow.getSomatoria() + calcRow.getValorJuros());
					calcRow.setSubTotalDelta(0.0);
					
					primeiroSubTotal = calcRow.getSubTotal();
					ultimoSubTotal = calcRow.getSubTotal();
					ultimoMes++;
					
					this.resultadoSimuladorList.add(calcRow);
				}else {
					ResultadoSimuladorEntity calcRow = new ResultadoSimuladorEntity();
					calcRow.setId(simuladorId);
					calcRow.setMes(ultimoMes);
					calcRow.setValor(ultimoSubTotal);
					calcRow.setAporte(this.aporteMensal);
					calcRow.setSomatoria(calcRow.getValor() + calcRow.getAporte());					
					calcRow.setJuros(this.taxaSelic/12);
					calcRow.setValorJuros( (calcRow.getSomatoria() * calcRow.getJuros() ) / 100);
					calcRow.setSubTotal(calcRow.getSomatoria() + calcRow.getValorJuros());
					calcRow.setSubTotalDelta(calcRow.getSubTotal() - primeiroSubTotal);
					
					ultimoSubTotal = calcRow.getSubTotal();
					ultimoMes++;
					
					this.resultadoSimuladorList.add(calcRow);
				}								
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			gerarGrafico();
			calcBigNumbers();
		}
	}

	public Double getTaxaSelic() {
		return taxaSelic;
	}

	public void setTaxaSelic(Double taxaSelic) {
		this.taxaSelic = taxaSelic;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getAporteMensal() {
		return aporteMensal;
	}

	public void setAporteMensal(Double aporteMensal) {
		this.aporteMensal = aporteMensal;
	}

	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}

	public List<ResultadoSimuladorEntity> getResultadoSimuladorList() {
		return resultadoSimuladorList;
	}

	public void setResultadoSimuladorList(List<ResultadoSimuladorEntity> resultadoSimuladorList) {
		this.resultadoSimuladorList = resultadoSimuladorList;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public Double getBigNumberValorTotalInvestido() {
		return bigNumberValorTotalInvestido;
	}

	public void setBigNumberValorTotalInvestido(Double bigNumberValorTotalInvestido) {
		this.bigNumberValorTotalInvestido = bigNumberValorTotalInvestido;
	}

	public Double getBigNumberValorTotalDeJuros() {
		return bigNumberValorTotalDeJuros;
	}

	public void setBigNumberValorTotalDeJuros(Double bigNumberValorTotalDeJuros) {
		this.bigNumberValorTotalDeJuros = bigNumberValorTotalDeJuros;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Boolean getPularPrimeiroMes() {
		return pularPrimeiroMes;
	}

	public void setPularPrimeiroMes(Boolean pularPrimeiroMes) {
		this.pularPrimeiroMes = pularPrimeiroMes;
	}


	
	
	
}
