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

export const { selectAll: selectUpcomingFlightsAdapter } = upcomingFlightsAdapter.getSelectors(
  (state) => state.flightApp.upcomingFlights
);
export const { selectAll: selectReservations } = reservationsAdapter.getSelectors(
  (state) => state.flightApp.reseservations
);

const FlightSlice = createSlice({
  name: 'flights',
  initialState: {
    reseservations: reservationsAdapter.getInitialState({}),
    upcomingFlights: upcomingFlightsAdapter.getInitialState({}),
  },
  reducers: {
    setSelectedReservation: {
      reducer: (state, action) => {
        state.SelectedReservation = action.payload;
      },
    },
    resetSelectedReservation: (state, action) => {
      state.searchText = ''; //action.null
    },
  },
  extraReducers: {
    [getUserReservations.fulfilled]: (state, action) => {
      reservationsAdapter.setAll(state.reseservations, action.payload);
    }, //console.log(action.payload)
    [getUpcomingFlights.fulfilled]: (state, action) => {
      upcomingFlightsAdapter.setAll(state.upcomingFlights, action.payload);
    }, //console.log(action.payload)
    // [createFeedback.fulfilled]: (state, action) => rezervationsAdapter.addOne,
    // [updateFeedback.fulfilled]: rezervationsAdapter.upsertOne,
    // [removeFeedback.fulfilled]: rezervationsAdapter.removeOne
  },
});

export const { setSelectedReservation, resetSelectedReservation } = FlightSlice.actions;

export default FlightSlice.reducer;
