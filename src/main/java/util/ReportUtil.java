package util;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportUtil implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception{

		/*Cria a lista de dados que vem do SQL da consulta feita*/
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relation") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(), jrBeanCollectionDataSource);

		return JasperExportManager.exportReportToPdf(impressoraJasper);

	}
	
	@SuppressWarnings("rawtypes")
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio,HashMap<String, Object> params, ServletContext servletContext) throws Exception{

		/*Cria a lista de dados que vem do SQL da consulta feita*/
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relation") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrBeanCollectionDataSource);

		return JasperExportManager.exportReportToPdf(impressoraJasper);

	}




}
