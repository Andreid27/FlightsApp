import { Card, Grid, styled, useTheme } from '@mui/material';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { getCheckIn } from 'app/redux/reducers/FlightSlice';
import axios from 'axios';
import { Fragment } from 'react';
import { useDispatch } from 'react-redux';
import * as apiSpec from './../../../../fake-db/apiSpec';
import { saveAs } from 'file-saver';

function urltoFile(url, filename, mimeType) {
  return fetch(url)
    .then(function (res) {
      return res.arrayBuffer();
    })
    .then(function (buf) {
      let newFile = new File([buf], filename, { type: mimeType });
      return newFile;
    });
}

//Usage example:
// urltoFile('data:application/pdf;base64,', 'hello.txt', 'application/pdf').then(function (file) {
//   console.log(file);
// });

const Base64Decode = () => {
  let textFile;
  let fileSave;
  const getFile = async () => {
    let response = await axios.get(apiSpec.GET_CHECK_IN, {
      params: {
        password: window.localStorage.getItem('password'),
        userId: window.localStorage.getItem('userId'),
        reservationId: '38',
      },
    });
    const data = await response.data;
    textFile = data;
    // console.log(textFile);
    urltoFile('data:application/pdf;base64,' + textFile, 'first.pdf', 'application/pdf').then(
      function (file) {
        console.log(file);
        fileSave = file;
      }
    );
  };

  function download(text, name, type) {
    var a = document.getElementById('button');
    // var file = new Blob(['firstTest'], { type: 'pdf' });
    console.log(fileSave);
    saveAs(fileSave);
  }

  return (
    <Fragment>
      <button onClick={getFile}>Click ME HERE</button>
      <button onClick={download}>SECOND BUTTON</button>
    </Fragment>
  );
};

export default Base64Decode;
