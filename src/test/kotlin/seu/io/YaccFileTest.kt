package seu.io

import org.junit.BeforeClass
import org.junit.Test

class YaccFileTest {

    companion object {
        private lateinit var yaccFile: YaccFile
        @BeforeClass
        @JvmStatic fun constructor() {
            yaccFile = YaccFile("resource/example.y")
        }
    }

    @Test
    fun readHeaders() {
        kotlin.test.assertEquals("#include <ctype.h>\n", yaccFile.headers.toString())
    }

    @Test
    fun readInstructions() {
        val instructions: HashMap<String, String> = HashMap()
        instructions["DIGIT"] = "%token"
        kotlin.test.assertEquals(instructions, yaccFile.instructions)
    }

    @Test
    fun readRules() {
        println("rules:")
        yaccFile.rules.forEach { (t, u) -> println(t.toString() + "\t" + u.toString()) }
        println("terminals:")
        yaccFile.terminals.forEach{t -> println(t)}
        println("non-terminals:")
        yaccFile.non_terminals.forEach{t -> println(t)}
    }

    @Test
    fun readUserSeg() {
        kotlin.test.assertEquals("""
        yylex() {
            int c;
            c = getchar();
            if (isdigit(c)) {
                yylval = c-'0';
                return DIGIT;
            }
            return c;
        }

        """.trimIndent(), yaccFile.userSeg.toString())
    }
}