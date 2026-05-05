package tp.avec_lambda;

import tp.data.Product;
import tp.util.ProductUtil;

import java.util.List;
import java.util.stream.Collectors;

public class AutresTpsAvecStreams {
	
	
	public static void main(String[] args) {
		//detailsInUppercaseWithError();
		detailsInUppercase();
		//detailsInUppercaseWithOptional();
		
		//valeur attendue: details=DELICATE;DELICATE;NO_DETAILS;SOLID;SOLID
	}
	
	public static void detailsInUppercaseWithError() {
		List<Product> listProd = ProductUtil.initSampleProductList();
		System.out.println("listProd="+listProd);
		String details = listProd.stream()
		         .map(p->fetchProductDetails(p.getId()))
		         .map(d-> d.toUpperCase())
		         .collect(Collectors.joining(";"));
		System.out.println("details="+details);
	}
	
	public static void detailsInUppercase() {
		List<Product> listProd = ProductUtil.initSampleProductList();
		System.out.println("listProd="+listProd);
		String details = listProd.stream()
		         .map(p->fetchProductDetails(p.getId()))
		         //.map(....)// à compléter "no_details" si null
		         .map(d-> d.toUpperCase())
		         .collect(Collectors.joining(";"));
		System.out.println("details="+details);
	}
	
	public static void detailsInUppercaseWithOptional() {
		List<Product> listProd = ProductUtil.initSampleProductList();
		System.out.println("listProd="+listProd);
		/*
		String details = listProd.stream()
		         .map(p->fetchOptionalProductDetails(p.getId()))
		         //.map(optionalDetails -> .....toUpperCase())//à compléter
		         .collect(Collectors.joining(";"));
		System.out.println("details="+details);
		*/
	}
	
	public static String fetchProductDetails(Long idProd) {
		switch(idProd.intValue()) {
		case 1,2:
			return "delicate";
		case 4,5: 
			return "solid";
		default :
			return null;
		}
	}
	
	/*
	public static Optional<String> fetchOptionalProductDetails(Long idProd) {
		//...
	}
	*/

}
