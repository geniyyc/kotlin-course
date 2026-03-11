import kotlin.test.Test
import kotlin.test.assertEquals

class FirstTest {

    @Test
    fun test() {
        assertEquals(5, 2 + 3)
    }

    @Test
    fun test2() {
        //given
        val a = 1
        val b = 2L

        //when
        val actual = a + b

        //then
        assertEquals(3, actual)
    }
}