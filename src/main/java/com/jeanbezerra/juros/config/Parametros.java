package com.jeanbezerra.juros.config;

public class Parametros {

	public static final Double TAXA_JUROS_ANO = 13.75;
	public static final Double TAXA_JUROS_MES = 1.1458;

	public Parametros() {
		// TODO Auto-generated constructor stub
	}

	public static Double getTaxaJurosAno() {
		return TAXA_JUROS_ANO;
	}

	public static Double getTaxaJurosMes() {
		return TAXA_JUROS_MES;
	}

}
