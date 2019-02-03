package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class JobPostingHandlerTest {

    private final String jobPosting1 = "{\"experienceRequirements\": \"5-7 \\u00e9v k\\u00f6z\\u00f6tt\", \"description\": \"<p><!-- name=\\\"JWRAP.ID\\\" content=\\\"12624395\\\" --><!-- name=\\\"JWRAP.DATE\\\" content=\\\"2018-07-21 19:13:33\\\" --><p><h3>Company Description</h3><p><p><strong>Do you want beneficial technologies being shaped by your ideas? Whether in the areas of mobility solutions, consumer goods, industrial technology or energy and building technology \\u2013 with us, you will have the chance to improve quality of life all across the globe. </strong></p><p><strong>Welcome to Bosch.</strong></p><p>Robert Bosch Kft. has been established in 1991 at Budapest. More than 2300 colleagues are working together as part of 13 business units. Over 1800 engineers are responsible for complex automotive projects within the Bosch Engineering Centre of Budapest. The site in Budapest fulfils the role of south-east European sales centre as well on the field of vehicle parts, vehicle-diagnostic equipment, power tools and thermo technical equipment, brand security technologies. The European Regional Transport Management Center (TMC) was founded also in Budapest by Robert Bosch Kft. in 2014.</p></p></p><p><h3>Job Description</h3><p><p>The division Chassis Systems Control (CC) develop innovative components,<br>systems and functions in the field of vehicle safety, vehicle dynamics, driver<br>assistance and automated driving. Through the combination of systems based<br>on cameras, radar, ultrasonic sensors, electric power steering and active or<br>passive safety systems we not only make driving safer and more comfortable<br>today but we are also building the basis for the autonomous driving of<br>tomorrow.</p><p><strong>Your contribution to something big:</strong><br>- Responsible to the economic success of the product<br>- Frequent interaction with customers and other stakeholders<br>- Understanding and translating customer needs to the program teams<br>- Developing and communicating prgram vision continuously<br>- Braking down program level vision into understandable epics, user stories and product roadmap<br>- Developing and maintaining product backlog<br>- Prioritizing the program level features<br>- Presenting vision at the program increment planning event<br>- Defining release schedule and program increments<br>- Approving program increments according to customer expectations<br>- Representing the project</p></p></p><p><h3>Qualifications</h3><p><p><strong>What distinguishes you:</strong></p><p>- Proven practical experience in relevant areas of SW/Algorithm Development<br>- Knowledge and practical application of agile methods in large organization<br>- Experience in large project management<br>- Experience in agile development methods<br>- SAFe\\u00ae Program manager certificate<br>- Experience working in an automotive environment<br>- Flexible, dynamic, customer and goal oriented, open-minded, likes challenges<br>- Willingness to regularly (~1/month) travel abroad<br>- Strong communication skills in English and/or German</p></p></p><p><h3>Additional Information</h3><p><p><strong>Your future job location offers you:</strong></p><p>Flexible worktime options, benefits and services, childcare offers, medical services, employee discounts, various sports and health opportunities, on-site parking, catering facilities, access to local public transport, room for creativity, urban infrastructures</p></p></p></p>\", \"title\": \"Program Manager in SAFe\", \"employmentType\": [\"FULL_TIME\"], \"hiringOrganization\": {\"logo\": \"https://securemedia.newjobs.com/clu/xjp2/xjp21021hux/branding/156686/bosch-csoport-logo.png\", \"@type\": \"Organization\", \"name\": \"Bosch csoport\"}, \"industry\": [\"Aut\\u00f3ipar, alkatr\\u00e9szgy\\u00e1rt\\u00e1s\", \"Elektronika\"], \"@type\": \"JobPosting\", \"educationRequirements\": \"F\\u0151iskolai vagy BSC diploma\", \"url\": \"https://allas-ajanlat.monster.hu/Program-Manager-in-SAFe-Budapest-CHR-Magyarorsz\\u00e1g-Bosch-csoport/11/198383328\", \"validThrough\": \"2018-08-20\", \"jobLocation\": {\"geo\": {\"latitude\": \"47.5\", \"@type\": \"GeoCoordinates\", \"longitude\": \"19.0833\"}, \"@type\": \"Place\", \"address\": {\"addressLocality\": \"Budapest\", \"addressRegion\": \"CHR\", \"addressCountry\": \"Magyarorsz\\u00e1g\", \"@type\": \"PostalAddress\"}}, \"@context\": \"http://schema.org\", \"identifier\": {\"@type\": \"PropertyValue\", \"value\": \"DA-296-4011754-14\", \"name\": \"Bosch csoport\"}, \"datePosted\": \"2018-07-21\", \"occupationalCategory\": [\"13119900: Business Operations Specialists, All Other\"], \"salaryCurrency\": \"HUF\"}";
    private final String jobPosting2 = "{\"description\": \"<div id=\\\"job_summary\\\" style=\\\"display:none\\\"></div>\\r\\n<span id=\\\"TrackingJobBody\\\" name=\\\"TrackingJobBody\\\">\\r\\n<div id=\\\"joboffer\\\">\\r\\n<div id=\\\"logo\\\"><a data-role=\\\"none\\\" href=\\\"http://www.4flow.com/\\\" target=\\\"_blank\\\"></a></div>\\r\\n\\r\\n<header></header>\\r\\n\\r\\n<div class=\\\"content\\\">\\r\\n  <p>As an award-winning employer and specialist for logistics and supply chain management, 4flow unites consulting, software development, supply chain services, and research in one innovative business model. <br>\\r\\nAt 4flow software, we provide our Java-based software solution 4flow vista\\u00ae for supply chain and transportation optimization to customers around the globe.<br><br>\\r\\n\\r\\n4flow is continuing to grow successfully again this year and is offering motivated graduates the possi\\u00adbility to start their career with a globally operating company at our office in Budapest, Hungary as a:</p>\\r\\n\\r\\n\\r\\n<h2>Junior Java Developer - Junior Java fejleszt\\u0151  <!-- <br />\\r\\n<span>Supply Chain Analyst</span> -->\\r\\n</h2> \\r\\n\\r\\n<div id=\\\"tasks\\\">\\r\\n<h3>Your responsibilities</h3>\\r\\n<p>As part of the 4flow software team, you will work in an innovative and creative environment where you can actively participate in a highly collaborative agile development process. Starting from day one, you will have the opportunity to realize your full potential by taking over responsibility in the further development, enhance\\u00adment, and maintenance of the 4flow software solutions. Among other things, you will help shape the software\\u2019s business logic in close cooperation with the product management team, develop our 4flow vista\\u00ae framework, or implement new architectural principles. Furthermore, you will be involved in the technical documentation of our software products and functionalities. </p>\\r\\n</div>\\r\\n\\r\\n<div id=\\\"profil\\\">\\r\\n<h3>Your profile</h3>\\r\\n <ul>\\r\\n \\t<li>Bachelor\\u2019s degree in computer science, engineering, business, or comparable studies\\r\\n<li>Strong Java programming skills and ability to apply development tools such as eclipse, git, and gradle\\r\\n<li>High awareness of code quality and software design as well as experience in team programming\\r\\n<li>Very good English skills as well as strong analytical and conceptual skills\\r\\n<li>Willingness to travel to Germany for initial training\\r\\n </ul>\\r\\n</div>\\r\\n\\r\\n<div id=\\\"offer\\\">\\r\\n<p>We offer a challenging position with excellent opportunities for your personal development. Benefit from working with an experienced team, an award-winning company culture, and from focused, individualized training.</p>\\r\\n</div>\\r\\n\\r\\n<p>Are you interested in joining 4flow? Then please apply online through our <a data-role=\\\"none\\\" href=\\\"https://www.4flow.com/careers/job-portal/ansicht/stellenangebot/junior-java-developer.html?no_cache=1&tx_jobmodelextension_jobofferplugin%5Bcontroller%5D=JobOffer\\\" target=\\\"_blank\\\">job portal</a> or call Henriette Hunold <br>\\r\\nat +49 30 39740-0. </p></div><!-- Ende \\\"content\\\" -->\\r\\n\\r\\n<address>4flow, Henriette Hunold, Hallerstra\\u00dfe 1, 10587 Berlin, Germany | <a data-role=\\\"none\\\" href=\\\"http://www.4flow.com/\\\" target=\\\"_blank\\\">www.4flow.com</a></address>\\r\\n\\r\\n<footer></footer> \\r\\n<div class=\\\"clear\\\"></div>\\r\\n</div><!-- Ende \\\"joboffer\\\" -->\\r\\n</span>\\r\\n\", \"title\": \"Junior Java Developer - Junior Java fejleszt\\u0151\", \"employmentType\": [\"FULL_TIME\"], \"industry\": [\"Sz\\u00e1ll\\u00edt\\u00e1s, sz\\u00e1ll\\u00edtm\\u00e1nyoz\\u00e1s, rakt\\u00e1roz\\u00e1s\"], \"@type\": \"JobPosting\", \"datePosted\": \"2018-06-22\", \"url\": \"https://allas-ajanlat.monster.hu/Junior-Java-Developer-Junior-Java-fejleszt\\u0151-Budapest-CHR-Magyarorsz\\u00e1g-4flow/11/197393793\", \"validThrough\": \"2018-07-22\", \"jobLocation\": {\"geo\": {\"latitude\": \"47.44\", \"@type\": \"GeoCoordinates\", \"longitude\": \"19.1\"}, \"@type\": \"Place\", \"address\": {\"addressLocality\": \"Budapest\", \"addressRegion\": \"CHR\", \"postalCode\": \"1203\", \"@type\": \"PostalAddress\", \"addressCountry\": \"Magyarorsz\\u00e1g\"}}, \"@context\": \"http://schema.org\", \"identifier\": {\"@type\": \"PropertyValue\", \"value\": \"DA-289-3994242\", \"name\": \"4flow\"}, \"hiringOrganization\": {\"logo\": \"https://securemedia.newjobs.com/clu/xjp4/xjp4016311dex/joblogo.gif\", \"@type\": \"Organization\", \"name\": \"4flow\"}, \"occupationalCategory\": [\"15113200: Software Developers, Applications\"], \"salaryCurrency\": \"HUF\"}";

    @Test
    public void getJobTitleFromPosting() {
        assertEquals("Program Manager in SAFe", JobPostingHandler.getJobTitleFromPosting(jobPosting1));
        assertEquals("Junior Java Developer - Junior Java fejlesztő", JobPostingHandler.getJobTitleFromPosting(jobPosting2));
    }
}