import { Button, Grid, Typography } from '@mui/material';
import { Box } from '@mui/system';
import useSettings from 'app/hooks/useSettings';
import React from 'react';
import { useSelector } from 'react-redux';
import CheckInDialog from './CheckInDialog';

function CheckInButton(props) {
  const { settings, updateSettings } = useSettings();
  const toggleCheckInDialog = () => {
    updateSettings({
      layout1Settings: {
        CheckInDialog: {
          open: !settings.layout1Settings.CheckInDialog.open,
        },
      },
    });
  };

  if (
    props.selectedReservation.flight.status_CIN === 'open' &&
    props.selectedReservation.persoaneCinNo >= parseInt(props.selectedReservation.seats)
  ) {
    return (
      <Grid item container justifyContent="center" style={{ padding: 15 }}>
        <Button variant="contained" onClick={toggleCheckInDialog}>
          Download Check In
        </Button>
        <CheckInDialog
          open={settings.layout1Settings.CheckInDialog.open}
          toggle={toggleCheckInDialog}
          type={'downloadCheckIn'}
        />
      </Grid>
    );
  } else if (
    props.selectedReservation.flight.status_CIN === 'open' &&
    props.selectedReservation.persoaneCinNo == 0
  ) {
    return (
      <Grid item container justifyContent="center" style={{ padding: 15 }}>
        <Button variant="outlined" onClick={toggleCheckInDialog}>
          Make Check-In
        </Button>
        <CheckInDialog
          open={settings.layout1Settings.CheckInDialog.open}
          toggle={toggleCheckInDialog}
          type={'makeCheckIn'}
        />
      </Grid>
    );
  } else {
    return (
      <Grid item container justifyContent="flex-start">
        <Button variant="text" onClick={toggleCheckInDialog}>
          <Typography>When will the check-in open?</Typography>
          <CheckInDialog
            open={settings.layout1Settings.CheckInDialog.open}
            toggle={toggleCheckInDialog}
            type={'infoCheckIn'}
          />
        </Button>
      </Grid>
    );
  }
}

export default CheckInButton;
