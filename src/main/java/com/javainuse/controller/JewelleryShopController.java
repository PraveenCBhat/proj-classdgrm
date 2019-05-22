package com.javainuse.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.javainuse.model.GenericKubernetesParams;
import com.javainuse.model.Product;
import com.javainuse.service.JewelleryShopService;

@RestController
public class JewelleryShopController {

	private final JewelleryShopService jewelleryShopService;

	@Autowired
	public JewelleryShopController(JewelleryShopService jewelleryShopService) {
		this.jewelleryShopService = jewelleryShopService;
	}

	@RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
	public Product getQuestions(@RequestParam(required = true) String type) {

		Product product = new Product();
		product.setType(type);

		jewelleryShopService.getProductDiscount(product);

		return product;
	}
	
	@RequestMapping(value = "/getK8s", method = RequestMethod.GET, produces = "application/json")
	public boolean getK8s(@RequestParam(required = true) String yaml) {

		Yaml yamlParser = new Yaml();
		Map<String, Object> yamlObj = (Map<String, Object>) yamlParser.load(yaml) ;
		
		GenericKubernetesParams gkParams = new GenericKubernetesParams();
		gkParams.setCmd("execute");
		
		//gkParams.setInternalMap(getSampleYaml());
		gkParams.setInternalMap(yamlObj);

		return jewelleryShopService.computeK8sRule(gkParams);	
	}
	@RequestMapping(value = "/canDeploy", method = RequestMethod.GET, produces = "application/json")
	public GenericKubernetesParams canDeploy(@RequestParam(required = true) String yaml) {
		Map<String,Object> obj = getSampleYaml();
		
		GenericKubernetesParams gkParams = new GenericKubernetesParams();
		boolean lastCallSucceeded = false;
		for(String key: obj.keySet()) {
			//try float first
			try {
				gkParams.setFloatValue( Float.valueOf(obj.get(key).toString()));
				lastCallSucceeded = true;
			}catch(Exception ex){
				;;//do nothing
			}
			if(!lastCallSucceeded) {
				//then try string
				try {
					gkParams.setStringValue(obj.get(key).toString());
					lastCallSucceeded = true;
				}catch(Exception ex){
					;;//do nothing
				}
			}
			
		}
		
		return gkParams;
	}
	private boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

	private Map<String,Object> getSampleYaml(){
		Yaml yamlParser = new Yaml();
		String yamlSample ="invoice: 34843\n" + 
				"date   : 2001-01-23\n" + 
				"bill-to: &id001\n" + 
				"    given  : Chris\n" + 
				"    family : Dumars\n" + 
				"    address:\n" + 
				"        lines: |\n" + 
				"            458 Walkman Dr.\n" + 
				"            Suite #292\n" + 
				"        city    : Royal Oak\n" + 
				"        state   : MI\n" + 
				"        postal  : 48046\n" + 
				"ship-to: *id001\n" + 
				"product:\n" + 
				"    - sku         : BL394D\n" + 
				"      quantity    : 4\n" + 
				"      description : Basketball\n" + 
				"      price       : 450.00\n" + 
				"    - sku         : BL4438H\n" + 
				"      quantity    : 1\n" + 
				"      description : Super Hoop\n" + 
				"      price       : 2392.00\n" + 
				"tax  : 251.42\n" + 
				"total: 4443.52\n" + 
				"comments: >\n" + 
				"    Late afternoon is best.\n" + 
				"    Backup contact is Nancy\n" + 
				"    Billsmer @ 338-4338.";
		
		Map<String, Object> obj = (Map<String, Object>) yamlParser.load(yamlSample) ;
		return obj;
	}
}
