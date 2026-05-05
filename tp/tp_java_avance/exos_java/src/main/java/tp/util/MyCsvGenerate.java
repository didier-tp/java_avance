package tp.util;

import tp.annotations.CsvIgnore;
import tp.data.Person;
import tp.data.Product;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

public class MyCsvGenerate {
	
	public static String generateLineOfCsv(Object obj,boolean forFirstLine) {
		StringBuilder sb = new StringBuilder();
		Class c = obj.getClass();
		for(Field f : c.getDeclaredFields()) {
			f.setAccessible(true); //pour avoir le droit de récupérer la valeur d'une chose "private"
			Object valueOfField = null;
			try {
				valueOfField = f.get(obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			CsvIgnore annotCsvIgnore = f.getAnnotation(CsvIgnore.class);
			if(annotCsvIgnore==null) {
				if(forFirstLine)
					sb.append(";" + f.getName());
				else
			      sb.append(";" + valueOfField);
			}
		}
		sb.deleteCharAt(0);
		return sb.toString();
	}

	public static void main(String[] args) {
		Person p1 = new Person(1L, "jean" , "Bon");
		p1.setEmail("jb@gmail.com"); p1.setAge(40); p1.setTaille(180);
		System.out.println(generateLineOfCsv(p1,true));
		System.out.println(generateLineOfCsv(p1,false));
		System.out.println("-----");
		Product prod1 = new Product(1L, "prod 1" , 123.0 , "Bon produit");
		System.out.println(generateLineOfCsv(prod1,true));
		System.out.println(generateLineOfCsv(prod1,false));

		//generate csv file from java collection:
		List<Product> productList = ProductUtil.initSampleProductList();
		//System.out.println("productList="+productList);
		String fileName="products.csv";
		try (PrintWriter writer = new PrintWriter(new File(fileName))) {
			writer.println(generateLineOfCsv(new Product(),true));
			for (Product p : productList) {
				//writer.println(p.toString());
				writer.println(generateLineOfCsv(p,false));
			}
		} catch (Exception e) {
			//throw new RuntimeException(e);
			System.err.println(e.getMessage());
		}
	}

}
