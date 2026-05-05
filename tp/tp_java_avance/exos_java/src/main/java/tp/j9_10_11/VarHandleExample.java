package tp.j9_10_11;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleExample {
	
	//varHandle = référence  sur une variable pour pouvoir la manipuler par la suite 
	private VarHandle sizeFieldOfAnySubObject = null; 
	public enum SubObjectType { machine, animal };
	Object subObject ; //anyThing with a ".size"
	
    public VarHandleExample(SubObjectType subObjectType) {
    	   switch(subObjectType) {
    	      case machine: this.subObject = new MachineWithSize(); break;
    	      case animal: this.subObject = new AnimalWithSize(); break;
    	   }
            try {
            	//initialisation du "varHandle" :
            	sizeFieldOfAnySubObject = MethodHandles.lookup()
				        .in(this.subObject.getClass())
				        .findVarHandle(this.subObject.getClass(), "size", Integer.class);
            	//ON A AFFAIRE ICI A UNE SORTE D'ATERNATIVE A L'INTROSPECTION/REFLECTION
            	//DE MANIERE A ACCEDER ET MANIPULER un attribut(variable de classe) d'un objet quasi quelconque
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
    }

  //récupération indirecte de la valeur de la taille
    public Integer getSize() {
    	if(sizeFieldOfAnySubObject==null) return null;
        return (Integer) sizeFieldOfAnySubObject.get(subObject);
    }

    //mise à jour indirecte de la valeur de la taille
    public void setSize(Integer size) {
    	if(sizeFieldOfAnySubObject!=null)
    		sizeFieldOfAnySubObject.set(subObject, size);
    }
    
    //affichage indirect de la valeur de la taille :
    public void printSize() {
    	System.out.println("size="+this.getSize());
    }


   
    private class AnimalWithSize {
    	private String name="strange animal";
        private Integer size = 50;
        
		@Override
		public String toString() {
			return "AnimalWithSize [name=" + name + ", size=" + size + "]";
		}
    }
    
    private class MachineWithSize {
    	private String label="good machine";
        private Integer size = 80;
        
		@Override
		public String toString() {
			return "MachineWithSize [label=" + label + ", size=" + size + "]";
		}
    }
    
    public static void main(String[] args) {
    	    System.out.println("For animal:");
			VarHandleExample varHandleEx = new VarHandleExample(SubObjectType.animal);
			varHandleEx.printSize();//50
			varHandleEx.setSize(2);
			varHandleEx.printSize();//2
			
			System.out.println("For machine:");
			varHandleEx = new VarHandleExample(SubObjectType.machine);
			varHandleEx.printSize();//80
			varHandleEx.setSize(2);
			varHandleEx.printSize();//2
	}
}
