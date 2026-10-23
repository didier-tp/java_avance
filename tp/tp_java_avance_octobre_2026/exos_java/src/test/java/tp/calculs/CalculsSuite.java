package tp.calculs;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

//@RunWith(JUnitPlatform.class) will be deprecated , use @Suite instead
////but @Suite not already supported by some old IDE (ex: ok with eclipse 2023-03)

/*
TP: faire en sorte que cette suite de tests
déclenche TestCalculsSimples , TestEmptySerie et TestSerie
---
utiliser @Suite et @SelectClasses
 */
@Suite
@SelectClasses({TestCalculsSimples.class , TestEmptySerie.class , TestSerie.class})
public class CalculsSuite {
    //déclenchable via mvn -Dtest=CalculsSuite test depuis un script  .bat/.sh
}
