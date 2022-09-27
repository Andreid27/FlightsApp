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
import _ from 'lodash';
import { isBefore } from 'date-fns';
import { Button, DialogActions, DialogContentText, DialogTitle, TextField } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import {
  addNewReservation,
  getDestinations,
  getRouteFlights,
  resetNewReservation,
  selectUpcomingFlightsAdapter,
  setNewReservation,
} from 'app/redux/reducers/FlightSlice';
import { useEffect } from 'react';
import styled from '@emotion/styled';

const CustomDialogTitle = styled(DialogTitle)(({ theme }) => ({
  paddingTop: '30px',
  marginBottom: '0px',
  color: 'black !important',
}));
const CustomDialogContentText = styled(DialogContentText)(({ theme }) => ({
  paddingTop: '0px',
  color: 'black !important',
}));

function Row(props) {
  const { row } = props;
  const [nrSeats, setNrSeats] = React.useState(1);
  const dispatch = useDispatch();
  const NewReservation = {
    flightId: row.id,
    flightNumber: row.flightNumber,
    seatsNumber: nrSeats,
  };

  useEffect(() => {
    if (props.open) {
      dispatch(setNewReservation(NewReservation));
    }
    props.clickedButton(NewReservation);
  }, [NewReservation.flightId, NewReservation.seatsNumber]);

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => props.onClick(props.open ? '' : row.id)}
          >
            {props.open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row" colSpan={2}>
          {row.departureLocation}
        </TableCell>
        <TableCell align="left" colSpan={2}>
          {row.landingLocation}
        </TableCell>
        <TableCell align="left">
          <Typography display="div">
            {new Date(row.departureTimestamp).toLocaleDateString('en-UK')}
          </Typography>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={props.open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Flight Details
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell component="th" scope="row" colSpan={2} sx={{ pl: 2 }}>
                      Company
                    </TableCell>
                    <TableCell colSpan={2} align="left">
                      Flight Number
                    </TableCell>
                    <TableCell align="left">Departure</TableCell>
                    <TableCell align="left">Landing</TableCell>
                    <TableCell align="left">Adults:</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  <TableCell align="left" colSpan={2} sx={{ pl: 2 }}>
                    {row.company}
                  </TableCell>
                  <TableCell align="left" colSpan={2}>
                    {row.flightNumber}
                  </TableCell>
                  <TableCell align="left">
                    {new Date(row.departureTimestamp).toLocaleTimeString([], {
                      hour: '2-digit',
                      minute: '2-digit',
                      hour12: false,
                    })}
                  </TableCell>
                  <TableCell align="left">
                    {new Date(row.landingTimestamp).toLocaleTimeString([], {
                      hour: '2-digit',
                      minute: '2-digit',
                      hour12: false,
                    })}
                  </TableCell>
                  <TableCell align="left">
                    <TextField
                      type="number"
                      value={nrSeats}
                      onChange={(event) => {
                        if (event.target.value >= 1) {
                          setNrSeats(event.target.value);
                        }
                      }}
                    ></TextField>
                  </TableCell>
                </TableBody>
                <TableRow>
                  <TableCell align="right" colSpan={7} sx={{ pl: 2 }}>
                    <Typography variant="subtitle2">
                      {`Total price for ${nrSeats} passangers is ${nrSeats * row.price} $`}
                    </Typography>
                  </TableCell>
                </TableRow>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

export default function FlightDetailsTable(props) {
  const dispatch = useDispatch();
  const [openRow, setOpenRow] = React.useState(null);
  const [NewReservation, setNewReservation] = React.useState(null);
  if (openRow == '') {
    dispatch(resetNewReservation());
  }

  const clickedButton = (reservation) => {
    // 👇️ take parameter passed from Child component
    setNewReservation(reservation);
  };

  function handleBookNow(event) {
    if (typeof openRow === 'number') {
      dispatch(addNewReservation(NewReservation));
      props.bookNowEnable(false);
    }
  }

  useEffect(() => {
    dispatch(getRouteFlights(props.flightFilter));
  }, [props.flightFilter]);

  const RouteFlights = useSelector(selectUpcomingFlightsAdapter);

  return RouteFlights[0] === undefined ? (
    <Box>
      <CustomDialogTitle
        sx={{ bgcolor: '#ffffff', color: 'primary.main' }}
        id="edit-apartment"
        component="div"
        gutterBottom
      >
        NO AVAILABLE FLIGHTS
      </CustomDialogTitle>
      <CustomDialogContentText sx={{ bgcolor: '#ffffff', color: 'primary.main', pl: 3, pb: 3 }}>
        We are sorry that there are no available flights at this moment for this flight route.
      </CustomDialogContentText>
    </Box>
  ) : (
    <>
      <CustomDialogTitle sx={{ bgcolor: '#ffffff', color: 'primary.main' }} id="edit-apartment">
        Available flights
      </CustomDialogTitle>
      <CustomDialogContentText sx={{ bgcolor: '#ffffff', color: 'primary.main', pl: 3, pb: 3 }}>
        {'We found the following flights for you:'}
      </CustomDialogContentText>
      <TableContainer component={Paper}>
        <Table aria-label="collapsible table">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell colSpan={2}>From</TableCell>
              <TableCell align="left" colSpan={2}>
                To
              </TableCell>
              <TableCell align="left">Date</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {_.filter(RouteFlights, function (f) {
              if (
                isBefore(props.flightFilter.departureTimestamp, new Date(f.departureTimestamp)) &&
                f.departureLocation === props.flightFilter.departureLocation &&
                f.landingLocation === props.flightFilter.landingLocation
              ) {
                return f;
              }
            }).map((row) => (
              <Row
                key={row.id}
                row={row}
                open={row.id == openRow}
                clickedButton={clickedButton}
                onClick={(id) => setOpenRow(id)}
              />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <DialogActions sx={{ bgcolor: '#ffffff' }}>
        <Button color="secondary">Cancel</Button>
        <Button
          variant="contained"
          onClick={handleBookNow}
          color="secondary"
          disabled={typeof openRow != 'number'}
        >
          Book Now
        </Button>
      </DialogActions>
    </>
  );
}
