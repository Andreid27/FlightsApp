import React from 'react';
import { useTheme } from '@mui/system';
import ReactEcharts from 'echarts-for-react';
import { isBefore } from 'date-fns';
import { useSelector } from 'react-redux';
import { selectReservations } from 'app/redux/reducers/FlightSlice';

function getUpcomingReservationsNumber(reservations) {
  let upComingReservations = 0;
  reservations.forEach((reservation) => {
    let departureTime = new Date(reservation.flight.departureTimestamp);
    let now = new Date();
    if (isBefore(departureTime, now)) {
      upComingReservations += 1;
    }
  });
  return upComingReservations;
}

function getDoneReservationsNumber(reservations) {
  let DoneReservations = 0;
  reservations.forEach((reservation) => {
    let landingTime = new Date(reservation.flight.landingTimestamp);
    let now = new Date();
    if (isBefore(landingTime, now)) {
      DoneReservations += 1;
    }
  });
  return DoneReservations;
}

const DoughnutChart = ({ height, color = [] }) => {
  const theme = useTheme();

  const reservations = useSelector(selectReservations);
  let UpcomingReservationsNumber = getUpcomingReservationsNumber(reservations);
  let DoneReservationsNumber = getDoneReservationsNumber(reservations);
  const option = {
    legend: {
      show: true,
      itemGap: 20,
      icon: 'circle',
      bottom: 0,
      textStyle: {
        color: theme.palette.text.secondary,
        fontSize: 13,
        fontFamily: 'roboto',
      },
    },
    tooltip: {
      show: false,
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)',
    },
    xAxis: [
      {
        axisLine: {
          show: false,
        },
        splitLine: {
          show: false,
        },
      },
    ],
    yAxis: [
      {
        axisLine: {
          show: false,
        },
        splitLine: {
          show: false,
        },
      },
    ],

    series: [
      {
        name: 'Traffic Rate',
        type: 'pie',
        radius: ['45%', '72.55%'],
        center: ['50%', '50%'],
        avoidLabelOverlap: false,
        hoverOffset: 5,
        stillShowZeroSum: false,
        label: {
          normal: {
            show: false,
            position: 'center', // shows the description data to center, turn off to show in right side
            textStyle: {
              color: theme.palette.text.secondary,
              fontSize: 13,
              fontFamily: 'roboto',
            },
            formatter: '{a}',
          },
          emphasis: {
            show: true,
            textStyle: {
              fontSize: '14',
              fontWeight: 'normal',
              color: 'rgba(15, 21, 77, 1)',
            },
            formatter: '{b} \n{c} ({d}%)',
          },
        },
        labelLine: {
          normal: {
            show: false,
          },
        },
        data: [
          {
            value: UpcomingReservationsNumber,
            name: 'Upcoming',
          },
          {
            value: DoneReservationsNumber,
            name: 'Done',
          },
          {
            value: reservations.length - UpcomingReservationsNumber - DoneReservationsNumber,
            name: 'Current',
          },
        ],
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  };

  return (
    <ReactEcharts
      style={{ height: height }}
      option={{
        ...option,
        color: [...color],
      }}
    />
  );
};

export default DoughnutChart;
