package tp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter  @Setter @ToString
public class Figure {
	public static final String TYPE_LINE = "line";
	public static final String TYPE_RECTANGLE = "rectangle";
	public static final String TYPE_CIRCLE = "circle";
	
	private String type;//"line" or "circle" or "rectangle" or ...
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private String color="black"; //ex: "black" , "red" or ...
	
	public Figure() {
		super();
	}

	public Figure(String type, int x1, int y1, int x2, int y2, String color) {
		super();
		this.type = type;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}

	public Figure(String type, int x1, int y1, int x2, int y2) {
		this(type,x1,y1,x2,y2,"black");
	}
	
	
}
