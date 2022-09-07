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
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
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
import {
  getUpcomingFlights,
  getUserReservations,
  selectUpcomingFlightsAdapter,
} from 'app/redux/reducers/FlightSlice';
import { LocalizationProvider } from '@mui/x-date-pickers-pro';
import { AdapterDayjs } from '@mui/x-date-pickers-pro/AdapterDayjs';
import { DateRangePicker } from '@mui/x-date-pickers-pro/DateRangePicker';
import { inputLabelClasses } from '@mui/material/InputLabel';
import { useSelector } from 'react-redux';
import _ from 'lodash';
import FlightDetailsTable from './FlightDetailsTable';
import { isBefore } from 'date-fns';

const CustomDialogTitle = styled(DialogTitle)(({ theme }) => ({
  paddingTop: '30px',
  marginBottom: '0px',
  color: 'black !important',
}));
const CustomDialogContentText = styled(DialogContentText)(({ theme }) => ({
  paddingTop: '0px',
  color: 'black !important',
}));

const SecondarySidebarContent = () => {
  const [open, setOpen] = useState(true);
  const [findFilterToggle, setFindFilterToggle] = useState(false);
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

  const upcomingFlights = useSelector(selectUpcomingFlightsAdapter);
  const [bookNowDisable, setBookNowDisable] = useState(true);
  const bookNowEnable = (en) => {
    // 👇️ take parameter passed from Child component
    setBookNowDisable(en);
  };

  const [flightFilter, setFlightFilter] = useState({
    departureLocation: null,
    landingLocation: null,
    departureTimestamp: date[0],
    landingTimestamp: date[1],
  });
  const updateFlightFilterState = (name, value) => {
    setFlightFilter((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleFindFlights = () => {
    setOpen(true);
    setFindFilterToggle(true);
  };

  const handleClose = () => {
    setOpen(false);
    toggle();
    setFindFilterToggle(false);
  };
  return findFilterToggle ? (
    <Dialog open={open} aria-labelledby="edit-apartment" fullWidth>
      <DialogContent
        sx={{
          color: 'error.main',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-around',
          minWidth: '100%',
          minHeight: '14rem',
          bgcolor: '#ffffff',
          pt: 0,
        }}
      >
        <CustomDialogTitle sx={{ bgcolor: '#ffffff', color: 'primary.main' }} id="edit-apartment">
          Available flights
        </CustomDialogTitle>
        <CustomDialogContentText sx={{ bgcolor: '#ffffff', color: 'primary.main', pl: 3, pb: 3 }}>
          {'We found the following flights for you:'}
        </CustomDialogContentText>
        <FlightDetailsTable
          flightFilter={flightFilter}
          flights={upcomingFlights}
          bookNowEnable={bookNowEnable}
        />
        <DialogActions sx={{ bgcolor: '#ffffff' }}>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button
            variant="contained"
            onClick={setFindFilterToggle}
            color="secondary"
            disabled={bookNowDisable}
          >
            Book Now
          </Button>
        </DialogActions>
      </DialogContent>
    </Dialog>
  ) : (
    <div>
      <Dialog open={open} aria-labelledby="edit-apartment" fullWidth>
        <CustomDialogTitle sx={{ bgcolor: '#ffffff', color: 'primary.main' }} id="edit-apartment">
          New Reservation
        </CustomDialogTitle>
        <CustomDialogContentText sx={{ bgcolor: '#ffffff', color: 'primary.main', pl: 3, pb: 0 }}>
          {'Please, enter the following details to book a new flight:'}
        </CustomDialogContentText>
        <DialogContent
          sx={{
            color: 'error.main',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-around',
            minWidth: '100%',
            minHeight: '14rem',
            bgcolor: '#ffffff',
            pt: 0,
          }}
        >
          <div style={{ display: 'flex', justifyContent: 'space-around', p: 0, columnGap: '20px' }}>
            <Autocomplete
              id="combo-box-demo"
              options={[...new Set(upcomingFlights.map((item) => item.departureLocation))]}
              style={{
                width: '45%',
              }}
              onChange={(event, value) => {
                updateFlightFilterState('departureLocation', value);
              }}
              getOptionLabel={(option) => option}
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
              options={[...new Set(upcomingFlights.map((item) => item.landingLocation))]}
              onChange={(event, value) => {
                updateFlightFilterState('landingLocation', value);
              }}
              getOptionLabel={(option) => option}
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
                inputFormat="DD/MM/YYYY"
                sx={{ width: '100%' }}
                onChange={(newValue) => {
                  setDate(newValue);
                  updateFlightFilterState('departureTimestamp', newValue[0].$d);
                  if (newValue[1]) {
                    updateFlightFilterState('landingTimestamp', newValue[1].$d);
                  }
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
        </DialogContent>
        <DialogActions sx={{ bgcolor: '#ffffff' }}>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button
            variant="contained"
            onClick={handleFindFlights}
            color="secondary"
            disabled={
              flightFilter.departureLocation === null || flightFilter.landingLocation === null
            }
          >
            Find Flights
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default SecondarySidebarContent;
