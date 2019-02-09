package jobposting

import org.joda.time.{DateTime => JodaDateTime}
import utils.JobPostingJsonUtils
import spray.json._

import scala.io.Source

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

object JobPostingCreator extends JobPostingJsonUtils {
  def fromJsonLine(jobPostingString: String): JobPosting = jobPostingString.parseJson.convertTo[JobPosting]
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