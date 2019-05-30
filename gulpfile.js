var gulp = require('gulp')

gulp.task('build', function() {
  return gulp
    .src('./node_modules/@groupdocs.examples.jquery/**')
    .pipe(gulp.dest('./src/main/resources/static/assets/'))
});

gulp.task('copy', function() {
  return gulp
      .src('./node_modules/@groupdocs.examples.angular/viewer/dist/**')
      .pipe(gulp.dest('./src/main/resources/static/'))
});