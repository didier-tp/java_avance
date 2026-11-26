package tp.j15_16_17;

import java.util.List;
import java.util.function.Function;

import tp.data.Figure;
import tp.util.FigureUtil;

public class SyntheseTestApp {

	private List<Figure> listeFigures = FigureUtil.initSampleFigureList();

	public static void main(String[] args) {
		// Exercice de synthese (avec code à compléter)
		SyntheseTestApp  syntheseTestApp = new SyntheseTestApp();
		syntheseTestApp.selonTypeFigure();
		syntheseTestApp.autresExperimentationsSelonInspirationDuMoment();
	}

	private void selonTypeFigure() {
		for(Figure fig : listeFigures) {
		/*
		//switch/case avec lambda expression
		//PHASE1: afficher LIGNE (en francais) si fig.type vaut Figure.TYPE_LINE
		//             RECTANGLE si fig.type vaut Figure.TYPE_RECTANGLE
        //             CERCLE (en francais)  si fig.type vaut Figure.TYPE_CIRCLE
		//             TYPE DE FIGURE INCONNU sinon (par défaut)
		switch(fig.getType()) {
		    case Figure.TYPE_LINE -> System.out.println("LIGNE");
		    case Figure.TYPE_RECTANGLE -> System.out.println("RECTANGLE");
		    case Figure.TYPE_CIRCLE -> System.out.println("CERCLE");
		    default -> System.out.println("TYPE DE FIGURE INCONNU");
		}
		*/
			//PHASE2: via switch/case en tant qu'expression retournant valeur
			//et pour chaque case :
			//      fonctionalité de la PHASE1 ET
			//      via yield , retourner une fonction de transformation adéquate (ex: FigureUtil::figureToCircle)
			Function<Figure,Object> transformationFunction =
					switch(fig.getType()) {
						case Figure.TYPE_LINE -> { System.out.println("LIGNE"); yield FigureUtil::figureToLine; }
						case Figure.TYPE_RECTANGLE ->  { System.out.println("RECTANGLE"); yield FigureUtil::figureToRectangle; }
						case Figure.TYPE_CIRCLE -> { System.out.println("CERCLE"); yield FigureUtil::figureToCircle; }
						default -> { System.out.println("TYPE DE FIGURE INCONNU"); yield FigureUtil::figureToUnknown; }
					};

			Object resTransformation = transformationFunction.apply(fig);
			System.out.println("resTransformation="+resTransformation);
			System.out.println("\t perimetre=" + perimetreOfRecord(resTransformation));
		}
	}

	//NB: dans ce projet de niveau java21 , lineColor inutilisé
	//NB: dans autre projet de niveau java25 , unamed variable _
	public double perimetreOfRecord(Object o){
		double perimetre= switch(o){
			case Dto.LineRecord(int x1,int y1,int x2,int y2,String lineColor) ->
				Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
			case Dto.RectangleRecord(int x1,int y1,int largeur,int hauteur,String fillColor) ->
					2 * (largeur + hauteur);
			case Dto.CircleRecord(int xc,int yc,int radius,String fillColor) ->
					2 * Math.PI * radius;
			default -> 0;
		};
		return perimetre;
	}

	private void autresExperimentationsSelonInspirationDuMoment() {
		String ch1= "   abc   ";
		System.out.println("***"+ch1+"***");
		String ch2=ch1.strip();
		System.out.println("***"+ch2+"***");

		String ch3 = "===".repeat(4);
		System.out.println("ch3: "+ch3);
		String ch4="ligne1\nl2\nligne3\nl4";
		ch4.lines()
				.filter(l->l.startsWith("ligne"))
				.forEach(l->System.out.println("l="+l));
	}
}
