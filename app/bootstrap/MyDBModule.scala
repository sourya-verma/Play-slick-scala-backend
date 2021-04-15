package bootstrap

import com.google.inject.AbstractModule
import controllers.Application

class MyDBModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[Application]).asEagerSingleton()
  }

}
