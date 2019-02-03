package utils

import org.joda.time.{DateTime => JodaDateTime}
import spray.json._
import DefaultJsonProtocol._

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

case class JobPosting (
                        crawlingDate: JodaDateTime,
                        postingId: String,
                        titleResultPage: String,
                        titlePosting: String,
                        company: String,
                        companyRatingValue: Double,
                        companyRatingCount: Int,
                        jobDescription: List[String],
                        postingTime: String,
                        jobLocation: String
                      )

object JobPostingCreator extends JsonSupport {
  def fromJsonLine(jobPostingString: String): JobPosting = jobPostingString.parseJson.convertTo[JobPosting]
}

trait JsonSupport extends Utils {

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

      override def write(obj: JobPosting) = ??? //TODO implementation
    }
}

case class JobPostingFile(jobPostingLines: List[String])

object JobPostingFile  {
  def apply(filePath: String): JobPostingFile = {
    val bufferedSource = Source.fromFile(filePath)
    try {
      val jobPostingLines = bufferedSource.getLines()
      new JobPostingFile(jobPostingLines.toList)
    } finally{
      bufferedSource.close()
    }
  }
}

trait Utils {
  def tryToInt( int: String ): Option[Int] = Try( int.toInt ).toOption
  def tryToDouble( double: String ): Option[Double] = Try( double.toDouble ).toOption
}

object Main extends App {
  //val exampleJobPosting = "{\"title_result_page\": \"Project Manager\", \"posting_id\": \"fc915f0fcbf9c0a6\", \"company_rating_value\": \"4.199999809265137\", \"company\": \"Network IT\", \"crawling_date\": \"2019-01-23\", \"title_posting\": \"Project Manager\", \"job_location\": \"Trafford\", \"company_rating_count\": \"5\", \"job_description\": [\"<div class=\\\"jobsearch-JobMetadataHeader icl-u-xs-mb--md\\\"><div class=\\\"jobsearch-JobMetadataHeader-item \\\"><span class=\\\"icl-u-xs-mr--xs\\\">\\u00a345,000 - \\u00a350,000 a year</span></div><div class=\\\"jobsearch-JobMetadataHeader-item  icl-u-xs-mt--xs\\\">Permanent</div></div>\", \"<div><div>Are you an ambitious and proven Project Manager, fed up with red tape and bureaucracy preventing successful project delivery? Do you currently run challenging and highly complex technical projects involving either digital, application, data, infrastructure, integration or managing business acquisitions? Are you unable to expand your portfolio to deliver the projects you need to expand your career? Would you like greater responsibility, variety and autonomy to deliver an exciting portfolio of projects?<br>\\n<br>\\nIf the answer is yes to all of these then our Client would like to speak to you! Based in the North Midlands our Client has been recognised and commended for thought leadership within their specialist services arena. They have achieved significant growth both organic and via acquisition and to support this, are looking for a Project Manager to join them.<br>\\n<br>\\nWorking within a new project management and change function the Project Manager will be involved from the early stages of project inception, through the full project life cycle to final delivery.<br>\\n<br>\\nEssential attributes to be the successful Project Manager are;<br>\\n<br>\\n<ul><li>Proven commercial project management experience of application or infrastructure projects</li></ul><br>\\n<ul><li>A good understanding of Prince II and its pragmatic application.</li></ul><br>\\n<ul><li>Experience of working within an Agile Project delivery function</li></ul><br>\\n<ul><li>A true technical appreciation of modern information technology</li></ul><br>\\n<ul><li>A true appetite and desire to progress</li></ul><br>\\n<ul><li>Proactive in nature</li></ul><br>\\nSpecific beneficial project experience<br>\\n<br>\\n<ul><li>Significant scale data migration / integration experience</li></ul><br>\\n<ul><li>Web transaction / on-line client systems</li></ul><br>\\n<ul><li>Business acquisition and operational change</li></ul><br>\\n<ul><li>Insurance services or similar industry experience</li></ul><br>\\nOur Client are entrepreneurial and thought leaders by nature and as such are looking for a like-minded Project Manager. Excellent career opportunities exist within this expanding Organisation.<br>\\n<br>\\nIf you are a proven and dynamic Project Manager who enjoys taking control and being accountable then apply now a starting basic salary of \\u00a345,000 - \\u00a350,000 plus benefits is offered.</div></div>\"], \"posting_time\": \" - 3 hours ago\"}"
//
  //val jobPosting = JobPostingCreator.fromJsonLine(exampleJobPosting)
//
  //println(jobPosting.crawlingDate.toDate)

  val jobPostingFile = JobPostingFile("/home/answeris42/Workspace/scraper/JobPostingScraper/data/2019_01_23_indeed_uk_fromage1.jl")
  jobPostingFile.jobPostingLines.map(x => println(JobPostingCreator.fromJsonLine(x).titlePosting))

}