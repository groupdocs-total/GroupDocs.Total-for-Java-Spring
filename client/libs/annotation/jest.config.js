module.exports = {
  name: 'annotation',
  preset: '../../jest.config.js',
  coverageDirectory: '../../coverage/libs/annotation',
  snapshotSerializers: [
    'jest-preset-angular/AngularSnapshotSerializer.js',
    'jest-preset-angular/HTMLCommentSerializer.js'
  ]
};
