package tp.j15_16_17;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TestDtoAsJson {

    @Test
    public void testCircleRecordAsJson(){
        Dto.CircleRecord c1 = new Dto.CircleRecord(50,40,60,"red");
        String c1JsonString = c1.toString();
        assertNotNull(c1JsonString);
        log.info("c1JsonString="+c1JsonString);
        assertThatJson(c1JsonString)
                .when(Option.IGNORING_EXTRA_FIELDS) //pour ok même si pas de "  "fillColor"  ou autre champ conidéré facultatif
                .isEqualTo("""
                      {
                      "yc": 40,
                      "xc": 50,
                       "radius" : 60
                     }""");

    }
}

/*

.isEqualTo("""
                      {
                      "xc": 50,
                      "yc": 40,
                       "radius" : 60,
                       "fillColor" : "red"
                     }""");
                     ok

          =====

          avec spring (équivalent prédéfini):

          assertThat(...)
                .isLenientlyEqualTo("""
                 [ {"numero":1,"label":"compteA","solde":40.0 },
                   {"numero":2,"label":"compteB","solde":90.0 }
                  ]
                """);

 */