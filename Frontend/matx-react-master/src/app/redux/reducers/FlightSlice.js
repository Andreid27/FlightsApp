import {
  createSlice,
  configureStore,
  createAsyncThunk,
  createEntityAdapter,
} from '@reduxjs/toolkit';
import axios from 'axios';
import * as apiSpec from './../../../fake-db/apiSpec';
import { useDispatch } from 'react';
import { element } from 'prop-types';

export const getUserReservations = createAsyncThunk(
  'flightApp/rezervations/getUserFlights',
  async (user) => {
    const response = await axios.get(apiSpec.REZERVATIONS, {
      params: {
        password: user.password,
        userId: user.userId,
      },
    });
    const data = await response.data;
    return data;
  }
);

export const getUpcomingFlights = createAsyncThunk(
  'flightApp/rezervations/getUpcomingFlights',
  async (user) => {
    const response = await axios.get(apiSpec.UPCOMING_FLIGHTS, {
      params: {
        password: window.localStorage.getItem('password'),
        userId: window.localStorage.getItem('userId'),
      },
    });
    const data = await response.data;

    return data;
  }
);

export const getRouteFlights = createAsyncThunk(
  'flightApp/rezervations/getRouteFlights',
  async (route) => {
    const response = await axios.post(apiSpec.LOGIN, {
      departureLocation: route.departure,
      landingLocation: route.destination,
      departureTimestamp: route.departureTimestamp,
      landingTimestamp: route.landingTimestamp,
      userId: window.localStorage.getItem('userId'),
      password: window.localStorage.getItem('password'),
    });
    const flights = response.data;

    return flights;
  }
);

export const getDestinations = createAsyncThunk(
  'flightApp/rezervations/getDestinations',
  async (user) => {
    const response = await axios.get(apiSpec.DESTIANTIONS, {
      params: {
        password: window.localStorage.getItem('password'),
        userId: window.localStorage.getItem('userId'),
      },
    });
    const data = await response.data;
    i = 0;

    return data;
  }
);

export const addNewReservation = createAsyncThunk(
  'flightApp/rezervations/addNewReservation',
  async (NewReservation) => {
    const response = await axios.post(apiSpec.NEW_RESERVATION, { NewReservation });
    const data = await response.data;

    return data;
  }
);

export const removeFeedback = createAsyncThunk(
  'feedbackApp/feedback/removeFeedback',
  async (feedbackId) => {
    const response = await axios.post('/api/notes-app/remove-note', { feedbackId });
    const data = await response.data;

    return data;
  }
);

const reservationsAdapter = createEntityAdapter({
  selectId: (reservation) => reservation.id,
});
const upcomingFlightsAdapter = createEntityAdapter({
  selectId: (upcomingFlight) => upcomingFlight.id,
});

let i = 0;
const destinationsAdapter = createEntityAdapter({
  selectId: (destination) => i++,
});

export const { selectAll: selectUpcomingFlightsAdapter } = upcomingFlightsAdapter.getSelectors(
  (state) => state.flightApp.upcomingFlights
);
export const { selectAll: selectReservations } = reservationsAdapter.getSelectors(
  (state) => state.flightApp.reseservations
);
export const { selectAll: selectDestinations } = destinationsAdapter.getSelectors(
  (state) => state.flightApp.destinations
);

const FlightSlice = createSlice({
  name: 'flights',
  initialState: {
    reseservations: reservationsAdapter.getInitialState({}),
    upcomingFlights: upcomingFlightsAdapter.getInitialState({}),
    destinations: destinationsAdapter.getInitialState({}),
  },
  reducers: {
    setSelectedReservation: {
      reducer: (state, action) => {
        console.log(action.payload);
        state.SelectedReservation = action.payload;
      },
    },
    resetSelectedReservation: (state, action) => {
      state.SelectedReservation = ''; //action.null
    },
    setNewReservation: {
      reducer: (state, action) => {
        state.NewReservation = action.payload;
      },
    },
    resetNewReservation: {
      reducer: (state, action) => {
        state.NewReservation = undefined;
      },
    },
  },
  extraReducers: {
    [getUserReservations.fulfilled]: (state, action) => {
      reservationsAdapter.setAll(state.reseservations, action.payload);
    },
    [getUpcomingFlights.fulfilled]: (state, action) => {
      upcomingFlightsAdapter.setAll(state.upcomingFlights, action.payload);
    },
    [getDestinations.fulfilled]: (state, action) => {
      destinationsAdapter.setAll(state.destinations, action.payload);
    },
    [addNewReservation.fulfilled]: (state, action) => reservationsAdapter.addOne,
    // [updateFeedback.fulfilled]: rezervationsAdapter.upsertOne,
    // [removeFeedback.fulfilled]: rezervationsAdapter.removeOne
  },
});

export const {
  setSelectedReservation,
  resetSelectedReservation,
  setNewReservation,
  resetNewReservation,
} = FlightSlice.actions;

export default FlightSlice.reducer;
