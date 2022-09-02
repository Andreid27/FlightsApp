import { Box, Card, Grid, Icon, IconButton, styled, Tooltip } from '@mui/material';
import { Small } from 'app/components/Typography';
import { selectReservations } from 'app/redux/reducers/FlightSlice';
import { isBefore } from 'date-fns';
import { useSelector } from 'react-redux';
import FlightTakeoffIcon from '@mui/icons-material/FlightTakeoff';
import FlightIcon from '@mui/icons-material/Flight';
import CardMembershipIcon from '@mui/icons-material/CardMembership';

const StyledCard = styled(Card)(({ theme }) => ({
  display: 'flex',
  flexWrap: 'wrap',
  alignItems: 'center',
  justifyContent: 'space-between',
  padding: '24px !important',
  background: theme.palette.background.paper,
  [theme.breakpoints.down('sm')]: { padding: '16px !important' },
}));

const ContentBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  flexWrap: 'wrap',
  alignItems: 'center',
  '& small': { color: theme.palette.text.secondary },
  '& .icon': { opacity: 0.6, fontSize: '44px', color: theme.palette.primary.main },
}));

const Heading = styled('h6')(({ theme }) => ({
  margin: 0,
  marginTop: '4px',
  fontSize: '14px',
  fontWeight: '500',
  color: theme.palette.primary.main,
}));
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

const StatCards = () => {
  const reservations = useSelector(selectReservations);
  let UpcomingReservationsNumber = getUpcomingReservationsNumber(reservations);
  const cardList = [
    {
      name: 'Your Upcoming Flights Reservations',
      amount: UpcomingReservationsNumber + ' Upcoming Reservations',
      icon: <FlightTakeoffIcon fontSize="large" />,
    },
    {
      name: 'Flights available to book',
      amount: 'Up to 500 flights',
      icon: <FlightIcon fontSize="large" />,
    },
    {
      name: 'Inventory Status',
      amount: '8.5% Stock Surplus',
      icon: 'attach_money',
    },
    {
      name: 'Your Total Reservations',
      amount: reservations.length + ' Total Reservations',
      icon: <CardMembershipIcon fontSize="large" />,
    },
  ];

  return (
    <Grid container spacing={3} sx={{ mb: '24px' }}>
      {cardList.map((item, index) => (
        <Grid item xs={12} md={6} key={index}>
          <StyledCard elevation={6}>
            <ContentBox>
              <Icon className="icon">{item.icon}</Icon>
              <Box ml="12px">
                <Small>{item.name}</Small>
                <Heading>{item.amount}</Heading>
              </Box>
            </ContentBox>

            <Tooltip title="View Details" placement="top">
              <IconButton>
                <Icon>arrow_right_alt</Icon>
              </IconButton>
            </Tooltip>
          </StyledCard>
        </Grid>
      ))}
    </Grid>
  );
};

export default StatCards;
