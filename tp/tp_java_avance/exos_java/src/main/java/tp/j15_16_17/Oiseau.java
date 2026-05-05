package tp.j15_16_17;

//déclarée final , la classe Chat ne peut pas avoir de sous classe
public final class Oiseau implements AnimalDomestique{

	@Override
	public void sayHello() {
		System.out.print("cuicui");
	}

	public void ronronner() {
		System.out.println(" cuicui ... ");
	}
}
