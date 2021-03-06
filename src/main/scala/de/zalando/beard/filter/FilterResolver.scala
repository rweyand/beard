package de.zalando.beard.filter

import de.zalando.beard.filter.implementations.DateFormatFilter
import de.zalando.beard.filter.implementations.TranslationFilter
import org.slf4j.LoggerFactory

import scala.collection.immutable.{Map, Seq, Set}

/**
  * @author dpersa
  */
trait FilterResolver {

  def registeredFilters = Seq(LowercaseFilter(), UppercaseFilter(), DateFormatFilter(), TranslationFilter())

  def filters: Map[String, Filter]

  def resolve(identifier: String, parameterNames: Set[String]): Option[Filter]
}

case class DefaultFilterResolver() extends FilterResolver {
  val logger = LoggerFactory.getLogger(this.getClass)
  
  override def resolve(identifier: String, parameterNames: Set[String]): Option[Filter] = {
    logger.info(s"Resolve filter ${identifier}")
    
    filters.get(identifier)
  }

  override def filters: Map[String, Filter] = {
    registeredFilters.map { case filter =>
      (filter.name, filter)
    }.toList.toMap[String, Filter]
  }
}
