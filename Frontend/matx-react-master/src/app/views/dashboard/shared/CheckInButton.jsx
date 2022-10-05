import { Button, Grid, Typography } from '@mui/material';
import { Box } from '@mui/system';
import React from 'react';
import { useSelector } from 'react-redux';

function CheckInButton(props) {
  console.log(
    props.selectedReservation.flight.status_CIN,
    props.selectedReservation.persoaneCinNo,
    props.selectedReservation.seats,
    props.selectedReservation.persoaneCinNo > parseInt(props.selectedReservation.seats)
  );
  if (
    props.selectedReservation.flight.status_CIN === 'open' &&
    props.selectedReservation.persoaneCinNo >= parseInt(props.selectedReservation.seats)
  ) {
    return (
      <Grid item container justifyContent="center" style={{ padding: 15 }}>
        <Button variant="contained">Download Check In</Button>
      </Grid>
    );
  } else if (
    props.selectedReservation.flight.status_CIN === 'open' &&
    props.selectedReservation.persoaneCinNo == 0
  ) {
    return (
      <Grid item container justifyContent="center" style={{ padding: 15 }}>
        <Button variant="outlined">Make Check-In</Button>
      </Grid>
    );
  } else {
    return (
      <Grid item container justifyContent="flex-start">
        <Button variant="text">
          <Typography>When will the check-in open?</Typography>
        </Button>
      </Grid>
    );
  }
}

export default CheckInButton;
