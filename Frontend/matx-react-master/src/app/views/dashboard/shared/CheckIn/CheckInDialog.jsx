import { Button, Dialog, DialogActions, DialogContent, Typography } from '@mui/material';
import React from 'react';
import InfoCheckIn from './CheckInDialogCases/InfoCheckIn';
import MakeCheckIn from './CheckInDialogCases/MakeCheckIn';

function CheckInDialog(props) {
  console.log(props);

  return (
    <Dialog open={props.open} aria-labelledby="edit-apartment" fullWidth>
      <DialogContent
        sx={{
          color: '.main',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-around',
          minWidth: '100%',
          minHeight: '14rem',
          bgcolor: '#ffffff',
          pt: 0,
        }}
      >
        {(() => {
          switch (props.type) {
            case 'makeCheckIn':
              return <MakeCheckIn />;
            case 'downloadCheckIn':
              return <Typography variant="outline">DOWNLOAD CHECK IN</Typography>;
            case 'infoCheckIn':
              return <InfoCheckIn open={props.open} />;
            default:
              return <InfoCheckIn />;
          }
        })()}
      </DialogContent>
      <DialogActions sx={{ bgcolor: '#ffffff' }}>
        <Button color="secondary" onClick={props.toggle}>
          Cancel
        </Button>
        <Button
          variant="contained"
          //   onClick={handleBookNow}
          color="secondary"
          disabled={typeof openRow != 'number'}
        >
          Book Now
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default CheckInDialog;
