package com.example.demo.certification;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import org.demoiselle.signer.core.repository.Configuration;
import org.demoiselle.signer.policy.impl.cades.factory.PKCS7Factory;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESSigner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SingerCertificate {
	

	
	@RequestMapping(value = "/certificate", params= {"path"}, method = RequestMethod.GET)
	public byte[] assinaturaCadesDetached(@RequestParam("path")String pathFileTxt) throws Exception {
		byte[] content = readContent(pathFileTxt);
		X509Certificate[] certificates = new X509Certificate[1];
		CAdESSigner singer = (CAdESSigner) new PKCS7Factory().factoryDefault();
		
		DataInputStream dis = new DataInputStream(new FileInputStream("/home/rcarauta/desenvolvimento/certificado/client.der"));
			
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readContent("/home/rcarauta/desenvolvimento/certificado/client.der"));
		
		certificates[0] = getCertificate();
		singer.setCertificates(certificates);
		
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privKey = factory.generatePrivate(spec);
		
		Configuration config = Configuration.getInstance();
		config.setOnline(false);	
		
		singer.setPrivateKey(privKey);
		
		return singer.doDetachedSign(content);
		
	}
	
	
   private X509Certificate getCertificate() throws IOException, Exception {
		String pathFile = "/home/rcarauta/desenvolvimento/certificado/client.crt";
		ByteArrayInputStream bytes = new ByteArrayInputStream(readContent(pathFile));
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");			
		return (X509Certificate) certFactory.generateCertificate(bytes);
	}
	
	private byte[] readContent(String pathFileTxt) throws IOException {
		return Files.readAllBytes(new File(pathFileTxt).toPath());
	}

}
