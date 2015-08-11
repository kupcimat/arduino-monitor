module.exports = function (grunt) {

    // load grunt plugins
    grunt.loadNpmTasks('grunt-react');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-uglify');

    // project configuration
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        clean: {
            build: {
                src: ['dist', 'src/main/resources/static/*.min.js']
            }
        },

        react: {
            build: {
                files: {
                    'dist/app.js': ['src/main/javascript/app.jsx']
                }
            }
        },

        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> (<%= pkg.version %>) <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                files: {
                    'src/main/resources/static/app.min.js': ['dist/app.js']
                }
            }
        }
    });

    // define grunt tasks
    grunt.registerTask('dist', ['react', 'uglify']);

};
