package com.javainuse.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javainuse.model.GenericKubernetesParams;
import com.javainuse.model.Product;

@Service
public class JewelleryShopService {

	private final KieContainer kieContainer;

	@Autowired
	public JewelleryShopService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public Product getProductDiscount(Product product) {
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(product);
		kieSession.fireAllRules();
		kieSession.dispose();
		return product;
	}
	public boolean computeK8sRule(GenericKubernetesParams gkParam) {
		KieSession kieSession = kieContainer.newKieSession("k8sRulesSession");
		kieSession.insert(gkParam);
		kieSession.fireAllRules();
		kieSession.dispose();
		return gkParam.isResult();
	}
}