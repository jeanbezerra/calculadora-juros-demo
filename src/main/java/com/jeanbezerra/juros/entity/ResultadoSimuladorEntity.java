package com.jeanbezerra.juros.entity;

import java.io.Serializable;

//@Entity
//@Table(name="resultado_simulador")
public class ResultadoSimuladorEntity implements Serializable {

	private static final long serialVersionUID = 5827693916181339509L;

	private String id;
	private Integer mes;
	private Double valor;
	private Double aporte;
	private Double somatoria;
	private Double juros;
	private Double valorJuros;
	private Double subTotal;
	private Double subTotalDelta;

	public ResultadoSimuladorEntity() {
	}

	public ResultadoSimuladorEntity(String id, Integer mes, Double valor, Double aporte, Double somatoria, Double juros,
			Double valorJuros, Double subTotal, Double subTotalDelta) {
		super();
		this.id = id;
		this.mes = mes;
		this.valor = valor;
		this.aporte = aporte;
		this.somatoria = somatoria;
		this.juros = juros;
		this.valorJuros = valorJuros;
		this.subTotal = subTotal;
		this.subTotalDelta = subTotalDelta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getAporte() {
		return aporte;
	}

	public void setAporte(Double aporte) {
		this.aporte = aporte;
	}

	public Double getSomatoria() {
		return somatoria;
	}

	public void setSomatoria(Double somatoria) {
		this.somatoria = somatoria;
	}

	public Double getJuros() {
		return juros;
	}

	public void setJuros(Double juros) {
		this.juros = juros;
	}

	public Double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(Double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getSubTotalDelta() {
		return subTotalDelta;
	}

	public void setSubTotalDelta(Double subTotalDelta) {
		this.subTotalDelta = subTotalDelta;
	}

	@Override
	public String toString() {
		return "ResultadoSimuladorEntity [id=" + id + ", mes=" + mes + ", valor=" + valor + ", aporte=" + aporte
				+ ", somatoria=" + somatoria + ", juros=" + juros + ", valorJuros=" + valorJuros + ", subTotal="
				+ subTotal + ", subTotalDelta=" + subTotalDelta + "]";
	}

}