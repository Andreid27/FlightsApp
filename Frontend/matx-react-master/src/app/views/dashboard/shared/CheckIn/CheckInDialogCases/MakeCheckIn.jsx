import { Button, Dialog, DialogActions, DialogContent, Typography } from '@mui/material';
import React from 'react';
import StepZilla from 'react-stepzilla';

const Step1 = (props) => {
  return (
    <div>
      <h1>Step 1</h1>
    </div>
  );
};

const Step2 = (props) => {
  const onClick = () => {
    props.jumpToStep(0);
  };
  return (
    <div>
      <h1>Step 2</h1>
      <button onClick={onClick}>Go to step 1</button>
    </div>
  );
};

const Step3 = (props) => {
  return (
    <div>
      <h1>Step 3</h1>
    </div>
  );
};

function MakeCheckIn(props) {
  const steps = [
    { name: 'Step 1', component: <Step1 /> },
    { name: 'Step 2', component: <Step2 /> },
    { name: 'Step 3', component: <Step3 /> },
  ];

  const [startAt, setStartAt] = React.useState(0);
  const onClick = () => {
    setStartAt(startAt + 1);
  };
  console.log('trdy');
  console.log(props);
  return (
    <div className="step-progress">
      <StepZilla startAtStep={startAt} steps={steps} showNavigation={true} showSteps={false} />
      <button onClick={onClick}>Control Start at</button>
    </div>
  );
}

export default MakeCheckIn;
