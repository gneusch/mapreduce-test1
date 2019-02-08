package utils

import jobposting.JobPosting
import spray.json._
import org.joda.time.{DateTime => JodaDateTime}

import scala.util.{Failure, Success, Try}
import DefaultJsonProtocol._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import esco._

trait JsonUtils {
  def getOptionalField[T: JsonReader](fields: Map[String, JsValue], key: String): Option[T] = {
    fields.get(key) match {
      case Some(jsValue) => Some(jsValue.convertTo[T])
      case None => None
    }
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
}

trait JobPostingJsonUtils extends JsonUtils with SupportTools {
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
}

trait EscoJsonUtils extends JsonUtils {
  def parseToSkillList(jsonStr: String): SkillList = {
    jsonStr.parseJson.convertTo[SkillList]
  }

  implicit def skillListUnmarshaller: FromEntityUnmarshaller[SkillList] = {
    Unmarshaller.stringUnmarshaller.map(parseToSkillList)
  }

  def parseToSkill(jsonStr: String): Skill = {
    jsonStr.parseJson.convertTo[Skill]
  }

  implicit def skillUnmarshaller: FromEntityUnmarshaller[Skill] = {
    Unmarshaller.stringUnmarshaller.map(parseToSkill)
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

  implicit val skillFormat: JsonFormat[Skill] =
    new JsonFormat[Skill] {
      override def read(json: JsValue): Skill = {
        val fields = json.asJsObject("Skill object expected").fields
        Skill(
          className = fields("className").convertTo[String],
          uri = fields("uri").convertTo[String],
          title = fields("title").convertTo[String],
          description = getOptionalField(fields, "description")(descriptionFormat),
          preferredLabel = fields("preferredLabel").convertTo[PreferredLabel],
          alternativeLabel = fields("alternativeLabel").convertTo[AlternativeLabel]
        )
      }

      override def write(obj: Skill): JsValue = ???
    }

  implicit val preferredLabelFormat: JsonFormat[PreferredLabel] =
    new JsonFormat[PreferredLabel] {
      override def read(json: JsValue): PreferredLabel = {
        val fields = json.asJsObject("PreferredLabel object expected").fields
        PreferredLabel(
          huLabel = fields(Languages.HU.name).convertTo[String],
          enLabel = fields(Languages.EN.name).convertTo[String]
        )
      }

      override def write(obj: PreferredLabel): JsValue = ???
    }

  implicit val alternativeLabel: JsonFormat[AlternativeLabel] =
    new JsonFormat[AlternativeLabel] {
      override def read(json: JsValue): AlternativeLabel = {
        val fields = json.asJsObject("AlternativeLabel object expected").fields
        AlternativeLabel(
          huLabels = getOptionalField[List[String]](fields, Languages.HU.name),
          enLabels = getOptionalField[List[String]](fields, Languages.EN.name)
        )
      }

      override def write(obj: AlternativeLabel): JsValue = ???
    }

  implicit val selfFormat: JsonFormat[Self] =
    new JsonFormat[Self] {
      override def read(json: JsValue): Self = {
        val fields = json.asJsObject("Self object expected").fields
        Self(
          resource_uri = fields("href").convertTo[String],
          title = fields("title").convertTo[String],
          uri = fields("uri").convertTo[String]
        )
      }

      override def write(obj: Self): JsValue = ???
    }

  implicit val descriptionFormat: JsonFormat[Description] =
    new JsonFormat[Description] {
      override def read(json: JsValue): Description =  {
        val fields = json.asJsObject("Description object expected").fields
        Description(
          enDescription = fields(Languages.EN.name).asJsObject("enDescription object expected").fields("literal").convertTo[String]
        )
      }

      override def write(obj: Description): JsValue = ???
    }

  implicit val topConceptListFormat: JsonFormat[TopConceptList] = jsonFormat2(TopConceptList)
}
