package utils

import jobposting.JobPosting
import spray.json._
import org.joda.time.{DateTime => JodaDateTime}

import scala.util.{Failure, Success, Try}
import DefaultJsonProtocol._
import esco.{PreferredLabel, SkillList, TopConceptList}

trait JsonUtils extends SupportTools {

  implicit val jodaDateTimeFormat: JsonFormat[JodaDateTime] =
    new JF[JodaDateTime] {
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
    new JF[JobPosting] {
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

      override def write(obj: JobPosting) = ??? //TODO implementation
    }

  implicit val skillListFormat: JsonFormat[SkillList] =
    new JF[SkillList] {
      override def read(json: JsValue): SkillList = {
        val fields = json.asJsObject("SkillList object expected").fields
        SkillList(
          links = fields("_links").convertTo[TopConceptList], //TODO implicit TopConceptList
          classId = fields("classId").convertTo[String],
          className = fields("className").convertTo[String],
          preferredLabel = fields("preferredLabel").convertTo[PreferredLabel],
          title = fields("title").convertTo[String],
          uri = fields("uri").convertTo[String]
        )
      }

      override def write(obj: SkillList): JsValue = ??? //TODO implementation
    }

  implicit val preferredLabelFormat: JsonFormat[PreferredLabel] =
    new JF[PreferredLabel] {
      override def read(json: JsValue): PreferredLabel = {
        case Seq(JsString(bg), JsString(cs), JsString(da), JsString(de), JsString(el), JsString(en),
                 JsString(es), JsString(et), JsString(fi), JsString(fr), JsString(ga), JsString(hr),
                 JsString(hu), JsString(is), JsString(it), JsString(lt), JsString(lv), JsString(mt),
                 JsString(nl), JsString(no), JsString(pl), JsString(pt), JsString(ro), JsString(sk),
                 JsString(sl), JsString(sv)) =>
          new PreferredLabel(bg, cs, da, de, el, en, es, et, fi, fr, ga, hr, hu, is, it, lt, lv, mt, nl, no, pl, pt, ro, sk, sl, sv)
        case _ => throw new DeserializationException("PreferredLabel expected")
      }

      override def write(obj: PreferredLabel): JsValue = ??? //TODO implementation
    }

}
