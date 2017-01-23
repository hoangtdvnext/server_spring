package vn.hoangtd.service;

import org.joda.time.LocalDate;
import vn.hoangtd.dto.ExaminationStatisticDTO;
import vn.hoangtd.dto.LineChartDTO;

import java.util.List;

/**
 * Created by hoangtd on 1/23/2017.
 */
public interface ExaminationStatisticService {
    /**
     * @param startDate
     * @param endDate
     * @param numberRecord
     * @return
     */
    List<ExaminationStatisticDTO> listStatistic(
            LocalDate startDate, LocalDate endDate, int numberRecord);

    /**
     * @param startDate
     * @param endDate
     * @param numberRecord <= 5
     * @return
     */
    LineChartDTO lineChartStatistic(
            LocalDate startDate, LocalDate endDate, int numberRecord);
}
