package tp.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface MyMinimum {
    int value();
    String message() default "Value is below the minimum allowed";
}


/*
class Person {
   @MyMinimum(0)  // pour interdire age négatif 
   Integer age ;
   
   


*/