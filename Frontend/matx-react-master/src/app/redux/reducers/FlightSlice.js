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

export const getUserFlights = createAsyncThunk(
  'flightApp/rezervations/getUserFlights',
  async (user) => {
    const response = await axios.get(apiSpec.REZERVATIONS, {
      params: {
        password: user.password,
        userId: user.userId,
      },
    });
    const data = await response.data;

    // const res = await axios.get('http://localhost:8080/api/usersFlights', {
    //   params: {
    //     'Test-Header': 'test-value',
    //   },
    // });

    // console.log(response.data);

    return data;
  }
);

export const updateFeedback = createAsyncThunk(
  'feedbackApp/feedback/updateFeedback',
  async (feedback) => {
    const response = await axios.post('/api/notes-app/update-note', { feedback });
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

export const { selectAll: selectReservations } = reservationsAdapter.getSelectors(
  (state) => state.flightApp
);

const FlightSlice = createSlice({
  name: 'flightApp/flights',
  initialState: reservationsAdapter.getInitialState({}),
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
    [getUserFlights.fulfilled]: (state, action) => {
      reservationsAdapter.setAll(state, action.payload);
    }, //console.log(action.payload)
    // [createFeedback.fulfilled]: (state, action) => rezervationsAdapter.addOne,
    // [updateFeedback.fulfilled]: rezervationsAdapter.upsertOne,
    // [removeFeedback.fulfilled]: rezervationsAdapter.removeOne
  },
});

export const { setSelectedReservation, resetSelectedReservation } = FlightSlice.actions;

export default FlightSlice.reducer;
