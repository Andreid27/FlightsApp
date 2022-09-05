import * as React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { Button, Card, styled } from '@mui/material';
import { convertHexToRGB } from 'app/utils/utils';
import { useSelector } from 'react-redux';
import { isBefore } from 'date-fns';
import { useTheme } from '@emotion/react';
import { makeStyles } from '@mui/styles';

const CardRoot = styled(Card)(({ theme }) => ({
  marginBottom: '15px',
  padding: '24px !important',
  [theme.breakpoints.down('sm')]: { paddingLeft: '16px !important' },
}));

const Small = styled('small')(({ bgcolor }) => ({
  width: 100,
  height: 25,
  color: '#fff',
  padding: '2px 8px',
  borderRadius: '4px',
  overflow: 'hidden',
  background: bgcolor,
  boxShadow: '0 0 2px 0 rgba(0, 0, 0, 0.12), 0 2px 2px 0 rgba(0, 0, 0, 0.24)',
}));

const Paragraph = styled('p')(({ theme }) => ({
  margin: 0,
  paddingTop: '24px',
  paddingBottom: '24px',
  color: theme.palette.text.secondary,
}));

function createData(name, calories, fat, carbs, protein, price) {
  return {
    name,
    calories,
    fat,
    carbs,
    protein,
    price,
    history: [
      {
        date: '2020-01-05',
        customerId: '11091700',
        amount: 3,
      },
      {
        date: '2020-01-02',
        customerId: 'Anonymous',
        amount: 1,
      },
    ],
  };
}
const useStyles = makeStyles({
  Header: {
    display: 'flex',
    justifyContent: 'space-between',
  },
});

function Row(props) {
  const { palette } = useTheme();
  const bgError = palette.success.main;
  const bgPrimary = palette.primary.main;
  const bgSecondary = palette.secondary.main;
  var dateOptions = {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
  };
  const classes = useStyles();
  const selectedReservation = useSelector(({ flightApp }) => flightApp.SelectedReservation);
  function hasDepartured(departure) {
    let departureTime = new Date(departure);
    let now = new Date();
    return isBefore(departureTime, now);
  }
  function isFling(flight) {
    let departureTime = new Date(flight.departureTimestamp);
    let landingTime = new Date(flight.landingTimestamp);
    let now = new Date();
    return isBefore(departureTime, now) && isBefore(now, landingTime);
  }

  return selectedReservation == null ? (
    <React.Fragment>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Box sx={{ margin: 1 }}>
            <Typography variant="h6" gutterBottom component="div">
              Reservation Info
            </Typography>

            <Table size="small" aria-label="purchases">
              <TableBody>
                <TableRow key={1}>
                  <Typography variant="overline">No flight selected</Typography>
                </TableRow>
              </TableBody>
            </Table>
          </Box>
        </TableCell>
      </TableRow>
    </React.Fragment>
  ) : (
    <React.Fragment>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Box sx={{ margin: 0 }}>
            <div className={classes.Header}>
              <Typography variant="h6" gutterBottom component="div">
                Reservation Info
              </Typography>
              {hasDepartured(selectedReservation.flight.departureTimestamp) ? (
                isFling(selectedReservation.flight) ? (
                  <Small bgcolor={bgSecondary}> {'     Is flying'}</Small>
                ) : (
                  <Small bgcolor={bgError}>Landed Succes</Small>
                )
              ) : (
                <Small bgcolor={bgPrimary}>Not Departured</Small>
              )}
            </div>
            <Table size="small" aria-label="purchases">
              <TableHead>
                <TableRow>
                  <TableCell colSpan={3}>From</TableCell>
                  <TableCell colSpan={3}>To</TableCell>
                  <TableCell align="left" colSpan={4}>
                    Departure/Landing Time
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow key={1}>
                  <TableCell component="th" scope="row" colSpan={3}>
                    {selectedReservation.flight.departureLocation}
                  </TableCell>
                  <TableCell colSpan={3}>{selectedReservation.flight.landingLocation}</TableCell>
                  <TableCell colSpan={4}>
                    <div>
                      <Typography variant="caption" display="div">
                        {new Date(selectedReservation.flight.departureTimestamp).toLocaleString(
                          'en-UK',
                          dateOptions
                        )}
                      </Typography>
                    </div>
                    <div>
                      <Typography variant="caption" display="div">
                        {new Date(selectedReservation.flight.landingTimestamp).toLocaleString(
                          'en-UK',
                          dateOptions
                        )}
                      </Typography>
                    </div>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </Box>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Table size="small">
            <TableHead>
              <TableRow>
                <TableCell colSpan={3}>Company</TableCell>
                <TableCell colSpan={3}>Seats</TableCell>
                <TableCell align="left" colSpan={4}>
                  Flight Number
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              <TableRow key={1}>
                <TableCell colSpan={3}>{selectedReservation.flight.company}</TableCell>
                <TableCell component="th" scope="row" colSpan={3}>
                  {selectedReservation.seats}
                </TableCell>
                <TableCell colSpan={4}>{selectedReservation.flight.flightNumber}</TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Table size="small">
            <TableHead>
              <TableRow>
                <TableCell colSpan={3}>Airplane</TableCell>
                <TableCell colSpan={3}>Model</TableCell>
                <TableCell align="left" colSpan={4}>
                  Passenger Capacity
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              <TableRow key={1}>
                <TableCell colSpan={3}>{selectedReservation.flight.airplane.Brand}</TableCell>
                <TableCell component="th" scope="row" colSpan={3}>
                  {selectedReservation.flight.airplane.Model}
                </TableCell>
                <TableCell colSpan={4}>
                  {selectedReservation.flight.airplane.max_capacity}
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

Row.propTypes = {
  row: PropTypes.shape({
    calories: PropTypes.number.isRequired,
    carbs: PropTypes.number.isRequired,
    fat: PropTypes.number.isRequired,
    history: PropTypes.arrayOf(
      PropTypes.shape({
        amount: PropTypes.number.isRequired,
        customerId: PropTypes.string.isRequired,
        date: PropTypes.string.isRequired,
      })
    ).isRequired,
    name: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    protein: PropTypes.number.isRequired,
  }).isRequired,
};

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99),
  createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
  createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
  createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
];

export default function ReservationInfo() {
  return (
    <CardRoot>
      <Table>
        <TableBody>
          <Row key={rows[1].name} row={rows[1]} />
        </TableBody>
      </Table>
    </CardRoot>
  );
}
