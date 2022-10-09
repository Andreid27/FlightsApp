import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { Typography } from '@mui/material';

export default function InfoCheckIn(props) {
  const scroll = 'paper';

  return (
    <div>
      <DialogContent dividers={scroll === 'paper'} sx={{ marginTop: 220 }}>
        <Typography variant="h3" sx={{ color: 'black' }}>
          Subscribe
        </Typography>
        <DialogContentText
          id="scroll-dialog-description"
          // ref={descriptionElementRef}
        >
          {[...new Array(50)]
            .map(
              () => `Cras mattis consectetur purus sit amet fermentum.
Cras justo odio, dapibus ac facilisis in, egestas eget quam.
Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
Praesent commodo cursus magna, vel scelerisque nisl consectetur et.`
            )
            .join('\n')}
        </DialogContentText>
      </DialogContent>
    </div>
  );
}
