package utils

import jobposting.JobPosting
import spray.json._
import org.joda.time.{DateTime => JodaDateTime}

import scala.util.{Failure, Success, Try}
import DefaultJsonProtocol._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import esco.{PreferredLabel, Self, SkillList, TopConceptList}

trait JsonUtils extends SupportTools {

  def parseToSkillList(jsonStr: String): SkillList = {
    jsonStr.parseJson.convertTo[SkillList]
  }

  implicit def skillListUnmarshaller: FromEntityUnmarshaller[SkillList] = {
    Unmarshaller.stringUnmarshaller.map(parseToSkillList)
  }

  implicit val jodaDateTimeFormat: JsonFormat[JodaDateTime] =
    new JsonFormat[JodaDateTime] {
      override def read(json: JsValue): JodaDateTime = json match {
        case JsString(string) => Try(JodaDateTime.parse(string)) match {
          case Success(validDateTime) => validDateTime
          case Failure(exception) => deserializationError(s"Could not parse $string as Joda DateTime.", exception)
        }
        case notAJsString => deserializationError(s"Expedted a String but got a $notAJsString")
      }

      override def write(obj: JodaDateTime): JsValue = JsString(obj.toString)
    }

  implicit val jobPostingFormat: JsonFormat[JobPosting] =
    new JsonFormat[JobPosting] {
      override def read(json: JsValue): JobPosting = {
        val fields = json.asJsObject("JobPosting object expected").fields
        JobPosting(
          crawlingDate = fields("crawling_date").convertTo[JodaDateTime],
          postingId = fields("posting_id").convertTo[String],
          titleResultPage = fields("title_result_page").convertTo[String],
          titlePosting = fields("title_posting").convertTo[String],
          company = fields("company").convertTo[String],
          companyRatingValue = tryToDouble(fields("company_rating_value").convertTo[String]) match {
            case Some(double) => double
            case None => 0d
          },
          companyRatingCount = tryToInt(fields("company_rating_count").convertTo[String]) match {
            case Some(int) => int
            case None => 0
          },
          jobDescription = fields("job_description").convertTo[List[String]],
          postingTime = fields("posting_time").convertTo[String],
          jobLocation = fields("job_location").convertTo[String]
        )
      }

      override def write(obj: JobPosting) = ???
    }

  implicit val skillListFormat: JsonFormat[SkillList] =
    new JsonFormat[SkillList] {
      override def read(json: JsValue): SkillList = {
        val fields = json.asJsObject("SkillList object expected").fields
        SkillList(
          links = fields("_links").convertTo[TopConceptList],
          classId = fields("classId").convertTo[String],
          className = fields("className").convertTo[String],
          preferredLabel = fields("preferredLabel").convertTo[PreferredLabel],
          title = fields("title").convertTo[String],
          uri = fields("uri").convertTo[String]
        )
      }

      override def write(obj: SkillList): JsValue = ???
    }

  implicit val preferredLabelFormat: JsonFormat[PreferredLabel] =
    new JsonFormat[PreferredLabel] {
      override def read(json: JsValue): PreferredLabel = {
        val fields = json.asJsObject("PreferredLabel object expected").fields
        PreferredLabel(
          huLabel = fields("hu").convertTo[String],
          enLabel = fields("en").convertTo[String]
        )
      }

      override def write(obj: PreferredLabel): JsValue = ???
    }

  implicit val selfFormat: JsonFormat[Self] = jsonFormat3(Self)

  implicit val topConceptListFormat: JsonFormat[TopConceptList] = jsonFormat2(TopConceptList)
}
