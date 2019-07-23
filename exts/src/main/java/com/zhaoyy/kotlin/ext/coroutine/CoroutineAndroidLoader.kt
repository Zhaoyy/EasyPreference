package com.zhaoyy.kotlin.ext.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.zhaoyy.kotlin.ext.loge
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * CoroutineAndroidLoader
 * by ZHAOYY
 * on 2019/4/16.
 */
internal class CoroutineLifecycleListener(private val deferred: Deferred<*>) : LifecycleObserver {
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun cancelCoroutine() {
    if (!deferred.isCancelled) {
      deferred.cancel()
    }
  }
}

/**
 * Creates a lazily started coroutine that runs <code>loader()</code>.
 * The coroutine is automatically cancelled using the CoroutineLifecycleListener.
 */
fun <T> LifecycleOwner.loadAsync(loader: suspend () -> T): Deferred<T> {
  val deferred = GlobalScope.async(context = Dispatchers.IO, start = CoroutineStart.LAZY) {
    loader()
  }

  lifecycle.addObserver(CoroutineLifecycleListener(deferred))
  return deferred
}

/**
 * Extension function on <code>Deferred<T><code> that creates a launches a coroutine which
 * will call <code>await()</code> and pass the returned value to <code>block()</code>.
 */
infix fun <T> Deferred<T>.then(block: suspend (T?, Throwable?) -> Unit): Job {
  return GlobalScope.launch(context = Dispatchers.Main) {
    try {
      block(this@then.await(), null)
    } catch (e: Exception) {
      // Just log the exception to confirm when we get cancelled (Expect JobCancellationException)
      loge("Exception in then()!")
      block(null, e)
    }
  }
}

