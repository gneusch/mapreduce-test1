package esco

import akka.http.scaladsl.unmarshalling.Unmarshal
import utils.{JsonUtils, RestTools}
import scala.concurrent.Future

case class Self(href: String, title: String, uri: String)
case class TopConceptList(hasTopConcept: List[Self], self: Self)
case class PreferredLabel(enLabel: String, huLabel: String)
case class SkillList(links: TopConceptList, classId: String, className: String, preferredLabel: PreferredLabel, title: String, uri: String)

object EscoSkill extends JsonUtils with RestTools with EscoService {

  final val SKILL_LIST_EN_URL = "http://data.europa.eu/esco/concept-scheme/skills&language=en"
  final val SKILL_LIST_HU_URL = "http://data.europa.eu/esco/concept-scheme/skills&language=hu"

  def getSkillList(langCode: String): Future[List[String]] = {
    val url = langCode match {
      case "hu" => SKILL_LIST_HU_URL
      case _ => SKILL_LIST_EN_URL
    }
    val responseFuture = getInFuture(s"$API_URL$RESOURCE_URI$url")
    responseFuture flatMap {
      response => Unmarshal(response.entity).to[SkillList] map {
        skillListResponse => skillListResponse.links.hasTopConcept map {
          self => self.title
        }
      }
    }
  }
}
