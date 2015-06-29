package org.ambling.chilltest

import scala.reflect.ClassTag
import scala.collection.mutable.ListBuffer

import java.io.ByteArrayOutputStream
import com.twitter.chill.ScalaKryoInstantiator
import com.twitter.chill.KryoSerializer
import com.twitter.chill.RichKryo
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output

object testChill {
  def registerClasses(kryo: Kryo) = {
    kryo.setRegistrationRequired(true)
    
    KryoSerializer.registerAll.apply(kryo)
    
    // test the default serializer of List
    println(kryo.getDefaultSerializer(List(0).getClass))
  }

  val classes: List[Class[_]] = List(
    classOf[java.util.HashSet[_]],
    classOf[java.util.HashMap[_, _]],
    classOf[Array[Double]],
    classOf[Array[scala.Tuple2[_,_]]],
    Class.forName("scala.None$"),
    Class.forName("scala.collection.immutable.$colon$colon"),
    Class.forName("scala.collection.immutable.Nil$"),
    Class.forName("scala.collection.mutable.WrappedArray$ofRef"),
    None.getClass,
    classOf[Some[_]],
    classOf[scala.collection.immutable.List[_]],
    classOf[scala.collection.immutable.Map[_,_]],
    classOf[scala.collection.mutable.ListBuffer[_]],
    classOf[scala.collection.mutable.ArrayBuffer[_]]
    )
  
  def main(args: Array[String]) = {
    
    val instantiator = new ScalaKryoInstantiator
    val kryo = instantiator.newKryo()
    registerClasses(kryo)
    val rich = new RichKryo(kryo)
    
    for (cls <- classes) {
      println(s"${cls.getName}: ${rich.alreadyRegistered(cls)}")
    }
  }
}