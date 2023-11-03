import kotlinx.coroutines.runBlocking
import org.example.MyClass
import org.example.MyContext
import org.mockito.Mockito
import org.mockito.kotlin.KStubbing
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.stubbing.OngoingStubbing
import kotlin.test.Test

class MainTest {

    @Test
    fun test_ok() {
        mock<MyClass> {
            on {
                with(any<MyContext>()) {
                    myMethod()
                }
            }.thenReturn("ok")
        }
    }

    @Test
    fun test_ok_if_not_suspended() {
        mock<MyClass> {
            onMyContext {
                myMethod()
            }.thenReturn("ok")
        }
    }

    @Test
    fun test_not_ok_if_suspended() {
        mock<MyClass> {
            onSuspendedMyContext {
                mySuspendedMethod()
            }.thenReturn("not_ok")
        }
    }
}

fun <T : Any, R> KStubbing<T>.onMyContext(
    m: context(MyContext) T.() -> R
): OngoingStubbing<R> {
    val context = mock<MyContext>()
    return Mockito.`when`(m(context, this@onMyContext.mock))
}

fun <T : Any, R> KStubbing<T>.onSuspendedMyContext(
    m: suspend context(MyContext) T.() -> R
): OngoingStubbing<R> {
    val context = mock<MyContext>()
    return runBlocking {
        Mockito.`when`(m(context, this@onSuspendedMyContext.mock))
    }
}
