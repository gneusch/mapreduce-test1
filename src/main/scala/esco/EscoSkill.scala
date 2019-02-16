package esco

import akka.http.scaladsl.unmarshalling.Unmarshal
import utils.{EscoJsonUtils, HttpTools, Languages, QueueingHttpsTools}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

case class Self(resource_uri: String, title: String, uri: String)
case class TopConceptList(hasTopConcept: List[Self], self: Self)
case class Description(enDescription: String)
case class PreferredLabel(enLabel: String, huLabel: String)
case class AlternativeLabel(enLabels: Option[List[String]], huLabels: Option[List[String]]) {
  def getLabelList(lang: Languages) = lang match {
    case Languages.HU => getList(huLabels)
      case _ =>  getList(enLabels)
    }

  private def getList(optList: Option[List[String]]): List[String] = optList match {
    case Some(list) => list
    case None => List()
  }
}
case class SkillList(links: TopConceptList, classId: String, className: String, preferredLabel: PreferredLabel, title: String, uri: String)
case class Skill(className: String, uri: String, title:String, description: Option[Description], preferredLabel: PreferredLabel, alternativeLabel: Option[AlternativeLabel])

trait EscoSkill extends EscoJsonUtils with HttpTools with EscoService {

  final val SKILL_URI = "/skill"
  final val SKILL_URL = s"$API_URL$RESOURCE_URI$SKILL_URI"
  final val SKILL_LIST_URL = "http://data.europa.eu/esco/concept-scheme/skills"
}
class EscoSkillHttp extends EscoService with EscoSkill {

  def getSkillTitleList(langCode: Languages): Future[List[String]] = {
    val url = getSkillListUrl(langCode)
    val responseFuture = getInFuture(s"$API_URL$RESOURCE_URI$URI_PROP$url")
    responseFuture flatMap {
      response => Unmarshal(response.entity).to[SkillList] map {
        skillListResponse => skillListResponse.links.hasTopConcept map {
          self => self.title
        }
      }
    }
  }

  def getSkillList(langCode: Languages): Future[SkillList] = {
    val url = getSkillListUrl(langCode)
    val responseFuture = getInFuture(s"$API_URL$RESOURCE_URI$URI_PROP$url")
    responseFuture flatMap {
      response => Unmarshal(response.entity).to[SkillList]
    }
  }

  def getSkillListUrl(langCode: Languages) = langCode match {
    case Languages.HU => s"$SKILL_LIST_URL$LANGUAGE_PROP${Languages.HU.name}"
    case _ => s"$SKILL_LIST_URL$LANGUAGE_PROP${Languages.EN.name}"
  }

  def getSkill(uri: String, langCode: Languages): Future[Skill] = {
    val url = s"$SKILL_URL$URI_PROP$uri$LANGUAGE_PROP${langCode.name}"
    val responeseFuture = getInFuture(url)
    responeseFuture flatMap {
      response => Unmarshal(response.entity).to[Skill]
    }
  }

  def getListOfSkills(lang: Languages): Future[List[Skill]] = {
    getSkillList(lang).flatMap(
      skillList => Future.sequence(skillList.links.hasTopConcept map {
        individualSkill => getSkill(individualSkill.uri, lang)
      })
    )
  }
}

class EscoQueuingSkillHttp(queueSize: Int = 32) extends EscoSkillHttp {

  def queueingHttpTools(url: String) = new QueueingHttpsTools(url, queueSize)
  val httpQuery = queueingHttpTools(ESCO_HOST)

  /*def getSkill(uri: String): Future[Skill] = {
    val responeseFuture = httpQuery.getInFuture(uri)
    responeseFuture flatMap {
      response => Unmarshal(response.entity).to[Skill]
    }
  }*/

  override def getSkill(uri: String, langCode: Languages): Future[Skill] = {
    val url = s"$API_URI$RESOURCE_URI$SKILL_URI$URI_PROP$uri$LANGUAGE_PROP${langCode.name}"
    val responeseFuture = httpQuery.getInFuture(url)
    responeseFuture flatMap {
      response => Unmarshal(response.entity).to[Skill]
    }
  }
}
