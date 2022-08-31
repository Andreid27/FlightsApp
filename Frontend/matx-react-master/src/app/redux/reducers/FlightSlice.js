import { createSlice,configureStore, createAsyncThunk, createEntityAdapter } from '@reduxjs/toolkit';
import axios from 'axios';
import * as apiSpec from './../../../fake-db/apiSpec';
import { useDispatch } from 'react';
import { element } from 'prop-types';


export const getUserFlights = createAsyncThunk('flightApp/rezervations/getUserFlights', async (user) => {
	const response = await axios.post(apiSpec.REZERVATIONS, { userName: user.userName, email: user.email, password: user.password, userId: user.userId });
	const data = await response.data;

	console.log(response.data);


	return data;
});

export const updateFeedback = createAsyncThunk('feedbackApp/feedback/updateFeedback', async feedback => {
	const response = await axios.post('/api/notes-app/update-note', { feedback });
	const data = await response.data;

	return data;
});

export const removeFeedback = createAsyncThunk('feedbackApp/feedback/removeFeedback', async feedbackId => {
	const response = await axios.post('/api/notes-app/remove-note', { feedbackId });
	const data = await response.data;

	return data;
});

const reservationsAdapter = createEntityAdapter({
  selectId: (reservation) => reservation.id,
});

export const {
	selectAll: selectReservations,
} = reservationsAdapter.getSelectors(state => state.flightApp);

const FlightSlice = createSlice({
	name: 'flightApp/flights',
	initialState: reservationsAdapter.getInitialState({}),
	reducers: {
		setFeedbacksSearchText: {
			reducer: (state, action) => {
				state.searchText = action.payload;
			},
			prepare: event => ({ payload: event.target.value || '' })
		},
		setFeedbacksFilterStars: {
			reducer: (state, action) => {
				state.filterStars = action.payload;
			},
			prepare: event => ({ payload: event.target.value || '' })
		},
		setFeedbacksFilterDate: {
			reducer: (state, action) => {
				state.filterDate = action.payload;
			},
			prepare: event => ({ payload: event || '' })
		},
		resetFeedbacksSearchText: (state, action) => {
			state.searchText = '';
		},
		resetFeedbacksFilterStars: (state, action) => {
			state.filterStars = '';
		},
		resetFeedbacksFilterDate: (state, action) => {
			state.filterDate = '';
		},
		toggleVariateDescSize: (state, action) => {
			state.variateDescSize = !state.variateDescSize;
		},
		openFeedbackDialog: (state, action) => {
			state.feedbackDialogId = action.payload;
		},
		closeFeedbackDialog: (state, action) => {
			state.feedbackDialogId = action.null;
		}
	},
	extraReducers: {
		[getUserFlights.fulfilled]: (state, action) => { reservationsAdapter.setAll(state, action.payload); }, //console.log(action.payload)
		// [createFeedback.fulfilled]: (state, action) => rezervationsAdapter.addOne,
		// [updateFeedback.fulfilled]: rezervationsAdapter.upsertOne,
		// [removeFeedback.fulfilled]: rezervationsAdapter.removeOne
	}
});

export const {
	setFeedbacksSearchText,
	resetFeedbacksSearchText,
	setFeedbacksFilterStars,
	resetFeedbacksFilterStars,
	setFeedbacksFilterDate,
	resetFeedbacksFilterDate,
	toggleVariateDescSize,
	openFeedbackDialog,
	closeFeedbackDialog
} = FlightSlice.actions;

export default FlightSlice.reducer;
