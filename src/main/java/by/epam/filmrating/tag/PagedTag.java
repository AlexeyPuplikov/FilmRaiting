package by.epam.filmrating.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class PagedTag extends BodyTagSupport {
    private static final String BEGIN = "<div class=\"container\"><div class=\"row\"><nav><ul class=\"pagination justify-content-center\">";
    private static final String END = "</ul></nav></div></div>";

    private int numberOfRecords;
    private int recordsPerPage;

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            int countOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            String commandName = "VIEW_FILMS";
            writer.print(BEGIN);
            for (int i = 1; i <= countOfPages; i++) {
                writer.print("<li class=\"page-item\">" + "<a class=\"page-link\" href=\"/controller?command=" + commandName + "&page="
                        + i + "\">"
                        + i + "</a>" + "</li>");
            }
            writer.print(END);
        } catch (IOException ex) {
            throw new JspException(ex);
        }
        return EVAL_BODY_INCLUDE;
    }
}
