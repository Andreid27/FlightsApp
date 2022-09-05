import React, { useEffect, useState } from 'react';
import {
  Autocomplete,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Icon,
  IconButton,
  InputLabel,
  Paper,
  TextField,
  Typography,
  useFormControl,
} from '@mui/material';
import { styled, useTheme } from '@mui/system';
import { Chatbox, ChatHead } from 'app/components';
import MatxCustomizer from '../MatxCustomizer/MatxCustomizer';
import ShoppingCart from '../ShoppingCart';
import { Span } from '../Typography';
import useSettings from 'app/hooks/useSettings';
import { makeStyles } from '@mui/styles';
import { useDispatch } from 'react-redux';
import { getUpcomingFlights, getUserReservations } from 'app/redux/reducers/FlightSlice';
import { LocalizationProvider } from '@mui/x-date-pickers-pro';
import { AdapterDayjs } from '@mui/x-date-pickers-pro/AdapterDayjs';
import { DateRangePicker } from '@mui/x-date-pickers-pro/DateRangePicker';
import { inputLabelClasses } from '@mui/material/InputLabel';

const CustomTextField = styled(TextField)(({ theme }) => ({
  color: theme.status.danger,
  '&.Mui-checked': {
    color: theme.status.danger,
  },

  '&.MuiInputLabel-root': {
    color: 'red !important',
  },
  '&.MuiFormLabel-root': {
    color: 'red !important',
  },
  '&.MuiOutlinedInput': {
    color: 'red !important',
  },
  '&.MuiTextField-root': {
    color: 'red !important',
  },
  '&.MuiOutlinedInput-notchedOutline': {
    color: 'red !important',
  },
  '&:hover': {
    backgroundColor: 'cyan !important',
  },
  '&.MuiInputLabel': {
    root: {
      color: 'red !important',
    },
  },
}));

const useStyles = makeStyles({
  option: {
    '& .MuiInputBase-input.MuiAutocomplete-input': {
      backgroundColor: '#fffff',
      color: 'green',
    },
    '&:hover': {
      backgroundColor: 'cyan !important',
    },
    TextField: {
      color: '#010b1c',
    },
  },
});

const SecondarySidebarContent = () => {
  const [open, setOpen] = useState(true);
  let nextDate = new Date();
  const [date, setDate] = useState([Date.now(), nextDate.setDate(nextDate.getDate() + 1)]);
  const dispatch = useDispatch();
  const { settings, updateSettings } = useSettings();
  const toggle = () => {
    updateSettings({
      secondarySidebar: { open: !settings.secondarySidebar.open },
    });
  };

  useEffect(() => {
    dispatch(getUpcomingFlights());
  }, []);

  // const handleClickOpen = () => {
  //   setOpen(true);
  // };

  const handleClose = () => {
    setOpen(false);
    toggle();
  };
  return (
    <div>
      <Dialog open={open} aria-labelledby="edit-apartment" fullWidth>
        <DialogTitle sx={{ bgcolor: '#ffffff', color: 'primary.main' }} id="edit-apartment">
          Edit
        </DialogTitle>
        <DialogContentText sx={{ bgcolor: '#ffffff', color: 'primary.main', pl: 3, pb: 0 }}>
          {'Please, edit the flat and the floor of your apartment.'}
        </DialogContentText>
        <DialogContent
          sx={{
            color: 'error.main',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-around',
            minWidth: '100%',
            minHeight: '20rem',
            bgcolor: '#ffffff',
            pt: 0,
          }}
        >
          <div style={{ display: 'flex', justifyContent: 'space-around', p: 0, columnGap: '20px' }}>
            <Autocomplete
              id="combo-box-demo"
              options={top100Films}
              style={{
                width: '45%',
              }}
              onChange={() => {}}
              getOptionLabel={(option) => option.label}
              PaperComponent={({ children }) => (
                <Paper style={{ color: '#010b1c', background: 'white' }}>{children}</Paper>
              )}
              placeholder="From"
              ListboxProps={{
                style: {
                  maxHeight: '16rem',
                  minWidth: '40%',
                },
              }}
              renderInput={(params) => (
                <>
                  <InputLabel htmlFor="anrede" sx={{ color: 'black', pl: 1 }}>
                    From
                  </InputLabel>
                  <TextField
                    {...params}
                    placeholder={'Departure City'}
                    sx={{ input: { color: '#010b1c' }, inputLbel: { color: '010b1c' } }}
                  />
                </>
              )}
            />
            <Autocomplete
              id="combo-box-demo"
              style={{
                width: '45%',
                color: '#010b1c',
              }}
              options={top100Films}
              onChange={() => {}}
              getOptionLabel={(option) => option.label}
              PaperComponent={({ children }) => (
                <Paper style={{ color: '#010b1c', background: 'white' }}>{children}</Paper>
              )}
              ListboxProps={{
                style: {
                  maxHeight: '16rem',
                  bgcolor: '#primary.main',
                  color: 'primary.main',
                  minWidth: '40%',
                },
              }}
              renderInput={(params) => (
                <>
                  <InputLabel htmlFor="anrede" sx={{ color: 'black', pl: 1 }}>
                    To
                  </InputLabel>
                  <TextField
                    {...params}
                    placeholder={'Landing City'}
                    sx={{ input: { color: '#010b1c' } }}
                  />
                </>
              )}
            />
          </div>
          <div style={{ display: 'flex', justifyContent: 'flex-start', paddingLeft: 8 }}>
            <LocalizationProvider
              dateAdapter={AdapterDayjs}
              localeText={{ start: 'Check-in', end: 'Check-out' }}
            >
              <DateRangePicker
                value={date}
                sx={{ width: '100%' }}
                onChange={(newValue) => {
                  setDate(newValue);
                }}
                renderInput={(startProps, endProps) => (
                  <React.Fragment>
                    <TextField
                      {...startProps}
                      sx={{ input: { color: '#010b1c' }, width: '75%' }}
                      InputLabelProps={{
                        sx: {
                          width: '100%',
                          input: { color: '#010b1c' },
                          [`&.${inputLabelClasses.shrink}`]: {
                            color: 'gray',
                          },
                        },
                      }}
                    />
                    <Box sx={{ color: '#010b1c', mx: 3 }}> to </Box>
                    <TextField
                      {...endProps}
                      sx={{ input: { color: '#010b1c' }, width: '30em' }}
                      InputLabelProps={{
                        sx: {
                          width: '100%',
                          input: { color: '#010b1c' },
                          [`&.${inputLabelClasses.shrink}`]: {
                            color: 'gray',
                          },
                        },
                      }}
                    />
                  </React.Fragment>
                )}
              />
            </LocalizationProvider>
          </div>
          <TextField autoFocus margin="dense" id="floor" label="Floor" type="text" fullWidth />
        </DialogContent>
        <DialogActions sx={{ bgcolor: '#ffffff' }}>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleClose} color="primary">
            Submit
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

const top100Films = [
  { label: 'The Shawshank Redemption', year: 1994 },
  { label: 'The Godfather', year: 1972 },
  { label: 'The Godfather: Part II', year: 1974 },
  { label: 'The Dark Knight', year: 2008 },
  { label: '12 Angry Men', year: 1957 },
  { label: "Schindler's List", year: 1993 },
  { label: 'Pulp Fiction', year: 1994 },
  {
    label: 'The Lord of the Rings: The Return of the King',
    year: 2003,
  },
];

export default SecondarySidebarContent;
