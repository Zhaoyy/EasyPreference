package com.zhaoyy.kotlin.ext.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * TestCoroutineException
 * by ZHAO YY
 * on 2019/8/2.
 */
fun main() {
  val handler = CoroutineExceptionHandler { _, throwable ->
    println(throwable.message)
  }

  val job = GlobalScope.launch(handler) {

  }

  dslTest {

  }

  dslTest({

  }, {

  })

}

fun dslTest(block: () -> Unit) {
  block()
}

fun dslTest(
  block: () -> Unit,
  handler: () -> Unit
) {

}

