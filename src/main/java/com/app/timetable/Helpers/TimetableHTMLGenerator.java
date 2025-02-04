package com.app.timetable.Helpers;

import com.app.timetable.Entity.TimetableEntity;

import java.util.ArrayList;

public class TimetableHTMLGenerator {

    public static String generateHTML(ArrayList<TimetableEntity> timetables,String title) {

        return """
                <html lang="en">
                <head>
                    <style>
                    
                    @page {
                        size: landscape;
                    }
                    
                        .innerBody{
                    width: 100%;
                    position: absolute;
                    top: 0px;
                    left: 0px;
                    height: 100%;
                    display: grid;
                    grid-template-rows: max-content auto;
                    padding: 0px;
                }
                
                .caption{
                    font-family: sans-serif;
                    text-align: center;
                    font-family: sans-serif;
                    font-weight: 800;
                    margin: 8px 24px;
                }
                .subHeadingStyle{
                    font-family: sans-serif;
                    text-align: center;
                    font-family: sans-serif;
                    font-weight: 600;
                    font-size: 16px;
                    margin: 8px 24px;
                }
                
                .tableHeader{
                      font-family: sans-serif;
                      font-size: 12px;
                      border-width: 1px 0 1px 1px;
                      border-color: #dddddd;
                      border-style: solid;
                      padding: 8px 16px;
                      margin: 8px;
                      text-align: start;
                      color: #505050;
                      font-weight: 200;
                  }
                
                
                  .tableHeaderLast{
                      font-family: sans-serif;
                      font-size: 12px;
                      border-width: 1px;
                      border-color: #dddddd;
                      border-style: solid;
                      padding: 8px 16px;
                      margin: 8px;
                      text-align: start;
                      color: #505050;
                      font-weight: 200;
                  }
                
                  .tableText{
                      font-family: sans-serif;
                      font-size: 14px;
                      padding: 8px 16px;
                      border-width: 0px 0px 1px 1px;
                      border-color: #dddddd;
                      border-style: solid;
                  }
                
                  .tableTextLast{
                      font-family: sans-serif;
                      font-size: 14px;
                      padding: 8px 16px;
                      border-width: 0px 1px 1px 1px;
                      border-color: #cccccc;
                      border-style: solid;
                  }
                
                 .tableRow{
                     padding: 8px;
                
                 }
                
                 .table{
                     border-collapse: collapse;
                     width: 100%;
                     height: max-content;
                     justify-self: center;
                
                 }
                
                    </style>
                
                </head>
                <body>
                
                    <div class="innerBody">
                    
                
                        <h1 class="caption">
                        Harare Institute of Technology
                        </h1>
                        <h1 class="subHeadingStyle">
                        """+
                        title+
                        """
                        </h1>
                
                        <table class="table">
                
                            <colgroup>
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                                <col />
                            </colgroup>
                
                            <thead class="tableRow">
                            <tr>
                                <th class="tableHeader">Date</th>
                                <th class="tableHeader">Time</th>
                                <th class="tableHeader">Course code</th>
                                <th class="tableHeader">Code narration</th>
                                <th class="tableHeader">Duration</th>
                                <th class="tableHeader">Number of students</th>
                                <th class="tableHeader">Venue</th>
                                <th class="tableHeader">Examiner</th>
                                <th class="tableHeader">Invigilators</th>
                                <th class="tableHeaderLast">In attendance</th>
                            </tr>
                
                            </thead>
                            <tbody>
                            """
                +getRows(timetables)
                +"""
                            </tbody>
                        </table>
                    </div>
                
                </body>
                </html>""";
    }

    private static String getRows(ArrayList<TimetableEntity> timetables) {
        StringBuilder rows = new StringBuilder();
        for(TimetableEntity timetable : timetables) {
            rows.append("""
                    <tr class="tableRow">
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getDate());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getTime());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getCourse());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getCourseNarration());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getDuration());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getNumOfStudents());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getVenue());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getExaminer());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableText">
                    """);
            rows.append(timetable.getInvigilator());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    <td class="tableTextLast">
                    """);
            rows.append(timetable.getInAttendance());
            rows.append("""
                    </td>
                    """);

            rows.append("""
                    </tr>
                    """);

        }

        return rows.toString();
    }
}
