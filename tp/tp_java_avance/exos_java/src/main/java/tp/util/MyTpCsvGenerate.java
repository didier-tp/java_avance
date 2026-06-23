package tp.util;

import tp.annotations.CsvIgnore;
import tp.data.Person;
import tp.data.Product;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

public class MyTpCsvGenerate {

	/**
	 *
	 * @param obj (java object , Product or Person or ...)
	 * @param forFirstLine (if true ---> fields_name , if false ---> fields_values )
	 * @return fields_name or fields_name separated by ; (ex: "id;label;price" or "1;prod 1;123.0" )
	 */
	public static String generateLineOfCsv(Object obj,boolean forFirstLine) {
		StringBuilder sb = new StringBuilder();
		//à coder en TP via introspection/reflexion
		//V1 : all existing fields
		//V2 : all fields without @CsvIgnore annotation (tp.annotations)
        Class<?> c = obj.getClass(); //meta description de la classe de l'objet obj
        try {
            Field[] tabChamps = c.getDeclaredFields();
            boolean firstField=true;
            for(Field f : tabChamps) {
                CsvIgnore annotCsvIgnore = f.getAnnotation(CsvIgnore.class);
                //si annotCsvIgnore==null , @CsvIgnore n'a pas été placé au dessus de f
                if(annotCsvIgnore!=null) continue; //on ignore ce champ
                if(forFirstLine){
                    if(!firstField)   sb.append(";");
                    sb.append(f.getName());
                }else{
                    f.setAccessible(true); //pour accéder aux parties privées
                    String fieldValue= f.get(obj)==null?null:f.get(obj).toString();
                    if(!firstField)   sb.append(";");
                    sb.append(fieldValue);
                }
                firstField=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

/*
Exemple de resultats:
id;firstName;lastName;email;age;taille
1;jean;Bon;jb@gmail.com;40;180
-----
id;label;price
1;prod 1;123.0
 */
