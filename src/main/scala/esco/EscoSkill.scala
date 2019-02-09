package esco

import akka.http.scaladsl.unmarshalling.Unmarshal
import utils.{EscoJsonUtils, Languages, RestTools}

import scala.concurrent.Future

case class Self(resource_uri: String, title: String, uri: String)
case class TopConceptList(hasTopConcept: List[Self], self: Self)
case class Description(enDescription: String)
case class PreferredLabel(enLabel: String, huLabel: String)
case class AlternativeLabel(enLabels: Option[List[String]], huLabels: Option[List[String]])
case class SkillList(links: TopConceptList, classId: String, className: String, preferredLabel: PreferredLabel, title: String, uri: String)
case class Skill(className: String, uri: String, title:String, description: Option[Description], preferredLabel: PreferredLabel, alternativeLabel: Option[AlternativeLabel])

object EscoSkill extends EscoJsonUtils with RestTools with EscoService {

  final val SKILL_URI = "/skill"
  final val SKILL_LIST_URL = "http://data.europa.eu/esco/concept-scheme/skills"

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
    val url = s"$API_URL$RESOURCE_URI$SKILL_URI$URI_PROP$uri$LANGUAGE_PROP${langCode.name}"
    val responeseFuture = getInFuture(url)
    responeseFuture flatMap {
      response => Unmarshal(response.entity).to[Skill]
    }
  }

}
