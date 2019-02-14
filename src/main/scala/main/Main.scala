package main

import esco.{EscoQueuingSkillHttp, EscoSkill, EscoSkillHttp}
import utils.Languages

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Main extends App {
  //val exampleJobPosting = "{\"title_result_page\": \"Project Manager\", \"posting_id\": \"fc915f0fcbf9c0a6\", \"company_rating_value\": \"4.199999809265137\", \"company\": \"Network IT\", \"crawling_date\": \"2019-01-23\", \"title_posting\": \"Project Manager\", \"job_location\": \"Trafford\", \"company_rating_count\": \"5\", \"job_description\": [\"<div class=\\\"jobsearch-JobMetadataHeader icl-u-xs-mb--md\\\"><div class=\\\"jobsearch-JobMetadataHeader-item \\\"><span class=\\\"icl-u-xs-mr--xs\\\">\\u00a345,000 - \\u00a350,000 a year</span></div><div class=\\\"jobsearch-JobMetadataHeader-item  icl-u-xs-mt--xs\\\">Permanent</div></div>\", \"<div><div>Are you an ambitious and proven Project Manager, fed up with red tape and bureaucracy preventing successful project delivery? Do you currently run challenging and highly complex technical projects involving either digital, application, data, infrastructure, integration or managing business acquisitions? Are you unable to expand your portfolio to deliver the projects you need to expand your career? Would you like greater responsibility, variety and autonomy to deliver an exciting portfolio of projects?<br>\\n<br>\\nIf the answer is yes to all of these then our Client would like to speak to you! Based in the North Midlands our Client has been recognised and commended for thought leadership within their specialist services arena. They have achieved significant growth both organic and via acquisition and to support this, are looking for a Project Manager to join them.<br>\\n<br>\\nWorking within a new project management and change function the Project Manager will be involved from the early stages of project inception, through the full project life cycle to final delivery.<br>\\n<br>\\nEssential attributes to be the successful Project Manager are;<br>\\n<br>\\n<ul><li>Proven commercial project management experience of application or infrastructure projects</li></ul><br>\\n<ul><li>A good understanding of Prince II and its pragmatic application.</li></ul><br>\\n<ul><li>Experience of working within an Agile Project delivery function</li></ul><br>\\n<ul><li>A true technical appreciation of modern information technology</li></ul><br>\\n<ul><li>A true appetite and desire to progress</li></ul><br>\\n<ul><li>Proactive in nature</li></ul><br>\\nSpecific beneficial project experience<br>\\n<br>\\n<ul><li>Significant scale data migration / integration experience</li></ul><br>\\n<ul><li>Web transaction / on-line client systems</li></ul><br>\\n<ul><li>Business acquisition and operational change</li></ul><br>\\n<ul><li>Insurance services or similar industry experience</li></ul><br>\\nOur Client are entrepreneurial and thought leaders by nature and as such are looking for a like-minded Project Manager. Excellent career opportunities exist within this expanding Organisation.<br>\\n<br>\\nIf you are a proven and dynamic Project Manager who enjoys taking control and being accountable then apply now a starting basic salary of \\u00a345,000 - \\u00a350,000 plus benefits is offered.</div></div>\"], \"posting_time\": \" - 3 hours ago\"}"
  //
  //val jobPosting = JobPostingCreator.fromJsonLine(exampleJobPosting)
  //
  //println(jobPosting.crawlingDate.toDate)

  //val jobPostingFile = JobPostingFile("/home/answeris42/Workspace/scraper/JobPostingScraper/data/2019_01_23_indeed_uk_fromage1.jl")
  //jobPostingFile.jobPostingLines.map(x => println(JobPostingCreator.fromJsonLine(x).titlePosting))

  val lang = Languages.EN
  val escoSkillHttp = new EscoQueuingSkillHttp(8192)

  val escoSkillList = escoSkillHttp.getListOfSkills(lang)

  val skillTitleList = escoSkillList map {
    skillList => skillList flatMap {
      skill => {
        val preferredLabel = skill.preferredLabel.enLabel
        val alternativeLabel = skill.alternativeLabel match {
          case Some(alternateLabel) => lang.name match {
            case Languages.HU.name => alternateLabel.getLabelList(Languages.HU)
            case _ => alternateLabel.getLabelList(Languages.EN)
          }
          case None => List()
        }
        preferredLabel :: alternativeLabel
      }
    }
  }
  Await.result(skillTitleList, Duration.Inf) map {
    var i = 0
    title => {
      i = i + 1
      println(s"$i: $title")
    }
  }
}
